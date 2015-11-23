package com.binaryworkspace.rcp.colorschemes.core.resources;

import java.awt.Color;

/**
 * Provides an AWT Color sampled from a predefined gradient color schemes. A
 * color sample based on a Linear Interpolation from a predefined gradient will
 * be return when a percentage value on the Decimal interval [0, 1] is provided.
 * <p>
 * The AWT Colors can be obtained either as a solid AWT Color when no alpha
 * value is provided, or as a transparent AWT Color when an alpha value is
 * provided.
 * 
 * @author Chris Ludka
 * 
 */
public final class AwtPointBasedColorSchemes {
	
	private AwtPointBasedColorSchemes() {
		// Hidden Constructor
	}
	
	// Helper conversion method
	private static Color getAwtColorFromArray(int[] array) {
		return new Color(array[0], array[1], array[2], array[3]);
	}

	/**
	 * Returns a solid color AWT color. Each color channel will be bounded to
	 * the octect interval of [0, 255].
	 * 
	 * @return
	 */
	public static Color getVisibleSpectrum(float percentage) {
		int[] array = ColorSchemePointBasedArray.getOctectVisibleSpectrum(percentage);
		return getAwtColorFromArray(array);
	}

	/**
	 * Returns an AWT Color with a custom value alpha channel where 0 is
	 * transparent and 255 is solid. Each color channel will be bounded to the
	 * octect interval of [0, 255].
	 * 
	 * @return
	 */
	public static Color getVisibleSpectrumWithAlpha(float percentage, int a) {
		int[] array = ColorSchemePointBasedArray.getOctectVisibleSpectrumWithAlpha(percentage, a);
		return getAwtColorFromArray(array);
	}
	
}