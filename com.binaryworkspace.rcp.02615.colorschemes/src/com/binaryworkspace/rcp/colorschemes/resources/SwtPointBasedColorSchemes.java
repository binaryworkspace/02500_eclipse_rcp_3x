package com.binaryworkspace.rcp.colorschemes.resources;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.binaryworkspace.rcp.colorschemes.core.resources.ColorSchemePointBasedArray;

/**
 * Provides an SWT Color sampled from a predefined gradient color schemes. A
 * color sample based on a Linear Interpolation from a predefined gradient will
 * be return when a percentage value on the Decimal interval [0, 1] is provided.
 * <p>
 * The SWT Colors can only be obtained as a solid SWT Color. SWT Color does not
 * provide direct support for alpha values.
 * 
 * @author Chris Ludka
 * 
 */
public final class SwtPointBasedColorSchemes {

	private SwtPointBasedColorSchemes() {
		// Hidden Constructor
	}

	// Helper conversion method
	private static RGB getRGBFromArray(int[] array) {
		return new RGB(array[0], array[1], array[2]);
	}

	/**
	 * Returns a solid color SWT color. Each color channel will be bounded to
	 * the octect interval of [0, 255].
	 * 
	 * @return
	 */
	public static Color getVisibleSpectrum(float percentage) {
		// Use the RGB codes as the key
		int[] array = ColorSchemePointBasedArray.getOctectVisibleSpectrum(percentage);
		RGB rgb = getRGBFromArray(array);
		String key = rgb.toString();
		
		// Check to see if color is already registered with JFaceResources
		if (!JFaceResources.getColorRegistry().hasValueFor(key)) {
			JFaceResources.getColorRegistry().put(key, getRGBFromArray(array));
		}

		// Return the color
		return JFaceResources.getColorRegistry().get(key);
	}
}