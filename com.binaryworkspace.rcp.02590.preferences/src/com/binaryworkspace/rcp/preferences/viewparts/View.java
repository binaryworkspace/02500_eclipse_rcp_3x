package com.binaryworkspace.rcp.preferences.viewparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.IServiceLocator;

import com.binaryworkspace.rcp.preferences.commands.EclispeCommandContributionItemType;
import com.binaryworkspace.rcp.preferences.preferences.PreferenceKeyType;

/**
 * This ViewPart using a TableViewer to listen and show PreferenceKeyType values
 * as they are changed in the IPreferenceStore.
 * 
 * @author Chris Ludka
 * 
 */
public class View extends ViewPart {
	
	public static final String ID = View.class.getName();

	private TableViewer tableViewer;

	private final IPreferenceStore preferencesStore = PlatformUI.getPreferenceStore();

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// Create TableViewer
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tableViewer.setContentProvider(new ViewContentProvider());
		tableViewer.setLabelProvider(new ViewLabelProvider());

		// Create ToolBar
		IToolBarManager manager = getViewSite().getActionBars().getToolBarManager();
		IServiceLocator serviceLocator = PlatformUI.getWorkbench();
		manager.add(EclispeCommandContributionItemType.PREFERENCES.getCommandContributionItemType(serviceLocator));
		manager.add(EclispeCommandContributionItemType.ABOUT.getCommandContributionItemType(serviceLocator));

		// Listen for Preference Changes
		preferencesStore.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				refresh();
			}
		});
		
		// Refresh
		refresh();
	}

	// Refreshes the TableViewer's content
	private void refresh() {
		// Get the current PreferenceKeyType values
		List<String> strList = new ArrayList<String>();
		for (PreferenceKeyType type : PreferenceKeyType.values()) {
			StringBuilder sb = new StringBuilder();
			sb.append(type.getDisplayName());
			sb.append(": [Defualt Value =");
			sb.append(type.getDefaultValue());
			sb.append(", Current Value = ");
			sb.append(preferencesStore.getString(type.getKey()));
			sb.append("]");
			strList.add(sb.toString());
		}

		// Provide the input to the ContentProvider
		tableViewer.setInput(strList.toArray());
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}
}