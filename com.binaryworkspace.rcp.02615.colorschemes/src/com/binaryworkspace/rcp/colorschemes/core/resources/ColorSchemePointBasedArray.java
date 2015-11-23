package com.binaryworkspace.rcp.colorschemes.core.resources;

import java.util.ArrayList;
import java.util.List;

import com.binaryworkspace.rcp.colorschemes.core.utils.ColorUtils;

/**
 * Provides centralized access to a gradient color schemes. A color sample based
 * on a Linear Interpolation from a predefined gradient will be return when a
 * percentage value on the Decimal interval [0, 1] is provided.
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
public final class ColorSchemePointBasedArray {

	private ColorSchemePointBasedArray() {
		// Hidden Constructor
	}

	// Visible Spectrum List
	private static List<float[]> visibleSpectrumList;

	/**
	 * Returns a solid color. Each color channel will be bounded to the decimal
	 * interval of [0.0f, 1.0f].
	 * 
	 * @return
	 */
	public static float[] getDecimalVisibleSpectrum(float percentage) {
		// Create the list
		if (visibleSpectrumList == null) {
			visibleSpectrumList = new ArrayList<float[]>();
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_00_BLACK.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_01_PURPLE.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_02_BLUE.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_03_TURQUOISE.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_04_GREEN.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_05_YELLOW.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_06_ORANGE.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_07_RED.getDecimalColor());
			visibleSpectrumList.add(ColorType.VISIBLE_SPECTRUM_08_BLACK.getDecimalColor());
		}

		// Return result
		return getFoatArray(visibleSpectrumList, percentage);
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
		// Get float array
		float[] floatArray = getDecimalVisibleSpectrum(percentage);

		// Convert to bounded octect int array
		int[] result = new int[floatArray.length];
		for (int i = 0; i < floatArray.length; i++) {
			result[i] = ColorUtils.getDecimalToOctect(floatArray[i]);
		}

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

	// Helper function that provides a defensive copied array
	private static float[] getFoatArray(List<float[]> list, float percentage) {
		// Validation
		if (list == null) {
			throw new RuntimeException("List<float[]> cannot be null.");
		} else if (list.size() < 2) {
			throw new RuntimeException("List<float[]> must contain at least 2 points.");
		}
		
		// Ensure percentage is bounded to the interval [0.0, 1.0]
		float boundedPercentage = ColorUtils.getBoundedDecimal(percentage);

		/*
		 * Determine the lowerIndex by applying the percentage (0.0 to 1.0) to
		 * the size of the list. For example, a list with a size of 6 would have
		 * index values of {0,1,2,3,4,5}. The 50% percentile index would be
		 * 0.5*(6-1) = 0.5*5 = 2.5. The lowerIndex would therefore be the
		 * integer part of 2.5 which would be 2.
		 */
		int lastIndex = list.size() - 1;
		float percentageIndex = boundedPercentage * lastIndex;
		int lowerIndex = Math.round(percentageIndex - 0.5f);

		// Check the index bounds
		if (lowerIndex < 0) {
			// Index values less than 1 return the color at index 0
			return list.get(0);
		} else if (lowerIndex >= lastIndex) {
			/*
			 * Index values greater than 1 or equal to the last index return the
			 * color of the last index
			 */
			return list.get(lastIndex);
		}

		/*
		 * The resulting color will a linear interpolation between each of the
		 * lowerIndex color and the upperIndex color rgb channels. The point on
		 * the line is determined by the fractional portion of the
		 * percentageIndex. So if percentageIndex = 2.1 would use the float[] at
		 * index 2 and index 3 with (2.1 - 2 = 0.1) 10% scaling towards the
		 * color at index 3. Likewise a percentageIndex = 2.9 would be 90%
		 * towards the linear scale of the float[] at index 3.
		 */
		float[] lowerColor = list.get(lowerIndex);
		float[] upperColor = list.get(lowerIndex + 1);
		float fractionIndexPart = percentageIndex - lowerIndex;
		float[] result = new float[4];
		for (int i = 0; i < 3; i++) {
			result[i] = lowerColor[i] + fractionIndexPart * (upperColor[i] - lowerColor[i]);
		}
		result[3] = 1.0f;
		return result;
	}
}