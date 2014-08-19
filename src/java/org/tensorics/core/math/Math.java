/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

/**
 * Implementations of this interface have to provide all the mathematical methods which will be required for numerical
 * calculations. The idea is, that there exists a default implementation, which only takes basic field operations to
 * perform all the calculations. Thus, the only required structure will be a field. Clearly, this might be not well
 * performing in many case. Thus it is possible to hook in dedicated implementations instead.
 * <p>
 * The general idea is to mimic the methods of {@link java.lang.Math}.
 * <p>
 * Ideas might also be taken from apache.commons.math and the FastMath class existing there.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements that can be handled by the math class.
 */
public interface Math<T> {

    /**
     * Has to return the first argument raised to the power of the second argument. This has to work for positive and
     * negative exponents.
     * 
     * @param base the number which should be raised to the power.
     * @param exponent the exponent which shall be applied to the base.
     * @return base^exponent
     */
    T pow(T base, T exponent);

}
