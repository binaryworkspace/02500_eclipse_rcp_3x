package com.binaryworkspace.rcp.colorschemes.core.resources;

import com.binaryworkspace.rcp.colorschemes.core.utils.ColorUtils;

/**
 * Provides centralized access to a gradient color schemes. A color sample based
 * on a Polynomial Least Squares Regression Fit from a predefined gradient will
 * be return when a percentage value on the Decimal interval [0, 1] is provided.
 * <p>
 * Color values are accessible as Arrays that can be freely mutated by the
 * consuming caller (e.g. The results are defensive copies). The Array will have
 * a size count of 4, where each index is assigned as:
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
public final class ColorSchemeFunctionBasedArray {

	private ColorSchemeFunctionBasedArray() {
		// Hidden Constructor
	}

	/**
	 * Returns a solid color. Each color channel will be bounded to the decimal
	 * interval of [0.0f, 1.0f].
	 * 
	 * @return
	 */
	public static float[] getDecimalVisibleSpectrum(float percentage) {
		// Get int array
		int[] intArray = getOctectVisibleSpectrum(percentage);

		// Convert to bounded decimal float array
		float[] result = new float[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = ColorUtils.getOctectToDecimal(intArray[i]);
		}

		// Return result
		return result;
	}

	/**
	 * Returns a color with a custom value alpha channel where 0.0f is
	 * transparent and 1.0f is solid. Each color channel will be bounded to the
	 * decimal interval of [0.0f, 1.0f].
	 * 
	 * @return
	 */
	public static float[] getDecimalVisibleSpectrumWithAlpha(float percentage, float a) {
		// Get non-alpha valued result
		float[] result = getDecimalVisibleSpectrum(percentage);

		// Validate that the provided alpha value is a bounded decimal
		result[3] = ColorUtils.getBoundedDecimal(a);

		// Return result
		return result;
	}

	/**
	 * Returns a solid color. Each color channel will be bounded to the octect
	 * interval of [0, 255].
	 * 
	 * @return
	 */
	public static int[] getOctectVisibleSpectrum(float percentage) {
		// Ensure percentage is bounded to the interval [0.0, 1.0]
		double x = ColorUtils.getBoundedDecimal(percentage);

		// Get Math.pow for x
		double x2 = Math.pow(x, 2.0);
		double x3 = Math.pow(x, 3.0);
		double x4 = Math.pow(x, 4.0);

		// Obtain color channels
		int[] result = new int[4];

		/*
		 * Red Octect Channel: 1.85813 + 1459.45 x - 9472.85 x^2 + 18540.8 x^3 -
		 * 10572.3 x^4
		 * 
		 * Value will be returned as an Octect that must be then bounded on the
		 * interval [0, 255].
		 */
		double value = 1.85813 + 1459.45 * x - 9472.85 * x2 + 18540.8 * x3 - 10572.3 * x4;
		result[0] = ColorUtils.getBoundedOctect(Math.round((float) value));

		/*
		 * Green Channel: 18.673 - 911.549 x + 7267.87 x^2 - 12198.1 x^3 +
		 * 5826.59 x^4, bounded on the interval [0, 1].
		 */
		value = 18.673 - 911.549 * x + 7267.87 * x2 - 12198.1 * x3 + 5826.59 * x4;
		result[1] = ColorUtils.getBoundedOctect(Math.round((float) value));

		/*
		 * Blue Channel: -43.5676 + 3460.92 x - 13727.5 x^2 + 18104.7 x^3 -
		 * 7826.49 x^4, bounded on the interval [0, 1].
		 */
		value = -43.5676 + 3460.92 * x - 13727.5 * x2 + 18104.7 * x3 - 7826.49 * x4;
		result[2] = ColorUtils.getBoundedOctect(Math.round((float) value));

		// Alpha channel
		result[3] = 255;

		// Return result
		return result;
	}

	/**
	 * Returns a color with a custom value alpha channel where 0 is transparent
	 * and 255 is solid. Each color channel will be bounded to the octect
	 * interval of [0, 255].
	 * 
	 * @return
	 */
	public static int[] getOctectVisibleSpectrumWithAlpha(float percentage, int a) {
		// Get non-alpha valued result
		int[] result = getOctectVisibleSpectrum(percentage);

		// Validate that the provided alpha value is a bounded octect
		result[3] = ColorUtils.getBoundedOctect(a);

		// Return result
		return result;
	}
}