/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

/**
 * This class provides some utility methods to evaluate the normal distribution
 * cumulative distribution function (CDF) and its inverse.
 * 
 * @author mihostet
 */
public class DoubleStatistics {

	private DoubleStatistics() {
		/* static methods only */
	}

	/**
	 * Calculate the standard Gaussian CDF (mu=0, sigma=1). Taken from: G.
	 * Marsaglia, Evaluating the Normal Distribution, JStatSoft 11 (4), 2004
	 * 
	 * @param value
	 *            The x value
	 * @return The standard Gaussian CDF
	 */
	public static double standardGaussianCumulativeDistributionFunction(double value) {
		if (value < -8.0) {
			return 0.0;
		}
		if (value > 8.0) {
			return 1.0;
		}
		double s = value, t = 0, b = value, q = value * value, i = 1;
		while (s != t) {
			s = (t = s) + (b *= q / (i += 2));
		}
		return 0.5 + s * Math.exp(-0.5 * q - 0.91893853320467274178);
	}

	/**
	 * Calculate the inverse standard Gaussian CDF by performing a recursive
	 * binary search on the CDF
	 * 
	 * @param value
	 *            The position to calculate the inverse CDF for
	 * @param low
	 *            The lower bound
	 * @param high
	 *            The upper bound
	 * @return
	 */
	private static double searchInverseGaussianCumulativeDistributionFunction(double value, double low, double high) {
		final double INVERSE_GAUSSIAN_ACCURACY = 0.00000001;
		double mid = low + (high - low) / 2.0;
		if (high - low < INVERSE_GAUSSIAN_ACCURACY) {
			return mid;
		}
		if (standardGaussianCumulativeDistributionFunction(mid) > value) {
			return searchInverseGaussianCumulativeDistributionFunction(value, low, mid);
		} else {
			return searchInverseGaussianCumulativeDistributionFunction(value, mid, high);
		}
	}

	/**
	 * Calculate the inverse Gaussian CDF (mu=0, sigma=1)
	 * 
	 * @param value
	 *            The position to calculate the inverse CDF for
	 * @return The inverse Gaussian CDF for y
	 */
	public static double inverseGaussianCumulativeDistributionFunction(double value) {
		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException("y must be in the range [0,1]");
		}
		return searchInverseGaussianCumulativeDistributionFunction(value, -8.0, 8.0);
	}

}
