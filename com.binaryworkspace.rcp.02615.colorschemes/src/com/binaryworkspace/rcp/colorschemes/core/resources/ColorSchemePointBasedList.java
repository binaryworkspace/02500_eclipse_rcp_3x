package com.binaryworkspace.rcp.colorschemes.core.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a List for a given Color sampled from a predefined gradient color
 * schemes. A color sample based on a Linear Interpolation from a predefined
 * gradient will be return when a percentage value on the Decimal interval [0,
 * 1] is provided.
 * <p>
 * Colors will be returned as Lists that can be freely mutated by the consuming
 * caller (e.g. The results are defensive copies). The Lists will be of length
 * 4, where:
 * 
 * <pre>
 * [0] = Red
 * [1] = Green
 * [2] = Blue
 * [3] = Alpha
 * </pre>
 * <p>
 * Color values are accessible in octect [0, 255] and Decimal [0, 1] formats in
 * order to support a variety of client technologies.
 * <p>
 * Colors can be obtained either as a solid color when no alpha value is
 * provided, or as transparent when an alpha value is provided.
 * 
 * @author Chris Ludka
 * 
 */
public final class ColorSchemePointBasedList {

	private ColorSchemePointBasedList() {
		// Hidden Constructor
	}

	// Helper conversion method
	private static List<Integer> getIntegerListFromArray(int[] array) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}
	
	// Helper conversion method
	private static List<Float> getFloatListFromArray(float[] array) {
		List<Float> result = new ArrayList<Float>();
		for (int i = 0; i < array.length; i++) {
			result.add(array[i]);
		}
		return result;
	}

	/**
	 * Returns a solid color. Each color channel will be bounded to the octect
	 * interval of [0, 255].
	 * 
	 * @return
	 */
	public static List<Integer> getOctectColor(float percentage) {
		return getIntegerListFromArray(ColorSchemePointBasedArray.getOctectVisibleSpectrum(percentage));
	}

	/**
	 * Returns a color with a custom value alpha channel where 0 is transparent
	 * and 255 is solid. Each color channel will be bounded to the octect
	 * interval of [0, 255].
	 * 
	 * @return
	 */
	public List<Integer> getOctectColorWithAlpha(float percentage, int a) {
		return getIntegerListFromArray(ColorSchemePointBasedArray.getOctectVisibleSpectrumWithAlpha(percentage, a));
	}

	/**
	 * Returns a solid color. Each color channel will be bounded to the decimal
	 * interval of [0.0f, 1.0f].
	 * 
	 * @return
	 */
	public List<Float> getDecimalColor(float percentage) {
		return getFloatListFromArray(ColorSchemePointBasedArray.getDecimalVisibleSpectrum(percentage));
	}

	/**
	 * Returns a color with a custom value alpha channel where 0.0f is
	 * transparent and 1.0f is solid. Each color channel will be bounded to the
	 * decimal interval of [0.0f, 1.0f].
	 * 
	 * @return
	 */
	public List<Float> getDecimalColorWithAlpha(float percentage, float a) {
		return getFloatListFromArray(ColorSchemePointBasedArray.getDecimalVisibleSpectrumWithAlpha(percentage, a));
	}
}