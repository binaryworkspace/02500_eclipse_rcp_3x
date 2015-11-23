package com.binaryworkspace.rcp.sleak.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class provides a String based report that summarizes the counts of
 * SWT objects that are using Native OS resources. SWT Development Tool with
 * Sleak must be installed and enabled in the Eclipse IDE before this utility
 * can be used.
 * <p>
 * Note that the logic presented in this class is originally based on
 * http://www.eclipse.org/articles/swt-design-2/Sleak.java.htm.
 * <p>
 * This utility class is instance based instead of static based because it
 * maintains stateful internal references. These internal references track the
 * resources being used for a given org.eclipse.swt.widgets.Display that was
 * provided during construction.
 * 
 * @author Chris Ludka
 * 
 */
public final class SleakUtil {

	/**
	 * Helper class to track resource usage based on a unique resource, where
	 * the key is the class name of the resource.
	 */
	private final class ResourceCounter {

		private String key;

		private long count = 0;

		public ResourceCounter(String key) {
			this.key = key;
		}
	}

	private final Map<String, ResourceCounter> reportMap = new LinkedHashMap<String, ResourceCounter>();

	private Object[] oldObjects = new Object[0];

	private Object[] objects = new Object[0];

	private Error[] errors = new Error[0];

	private Display display;

	public SleakUtil(Display display) {
		this.display = display;
	}

	/**
	 * Returns a String based report that summarizes the counts of SWT objects
	 * that are using Native OS resources.
	 * 
	 * @return
	 */
	public String getReport() {
		// Check tracking
		DeviceData deviceData = display.getDeviceData();
		if (!deviceData.tracking) {
			System.out.println(new RuntimeException("Warning: Device is not tracking resource allocation."));
		}

		// Get objects
		Object[] newObjects = deviceData.objects;
		Error[] newErrors = deviceData.errors;
		Object[] diffObjects = new Object[newObjects.length];
		Error[] diffErrors = new Error[newErrors.length];

		// Get object counts
		int count = 0;
		for (int i = 0; i < newObjects.length; i++) {
			int index = 0;
			while (index < oldObjects.length) {
				if (newObjects[i] == oldObjects[index])
					break;
				index++;
			}
			if (index == oldObjects.length) {
				diffObjects[count] = newObjects[i];
				diffErrors[count] = newErrors[i];
				count++;
			}
		}

		// Update arrays
		objects = new Object[count];
		errors = new Error[count];
		System.arraycopy(diffObjects, 0, objects, 0, count);
		System.arraycopy(diffErrors, 0, errors, 0, count);

		// Build report
		reportMap.clear();
		for (int i = 0; i < objects.length; i++) {
			// Determine if this is a new kind of resource
			Object obj = objects[i];
			String key = obj.getClass().getSimpleName();
			if (!reportMap.containsKey(key)) {
				reportMap.put(key, new ResourceCounter(key));
			}

			// Increment the resource count
			ResourceCounter resourceCounter = reportMap.get(key);
			resourceCounter.count++;
		}

		// Print the result
		StringBuilder sb = new StringBuilder();
		long total = 0;
		boolean isFirst = true;
		for (Entry<String, ResourceCounter> entry : reportMap.entrySet()) {
			ResourceCounter counter = entry.getValue();
			total = total + counter.count;
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append("\n");
			}
			sb.append(counter.key);
			sb.append(": ");
			sb.append(counter.count);
		}
		sb.append("\n");
		sb.append("Total: ");
		sb.append(total);
		sb.append("\n");

		return sb.toString();
	}
}