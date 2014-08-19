/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

/**
 * This interface provides methods for field elements, whose usage is highly discouraged and should be avoided.
 * Currently, they are necessary, mainly because no unit-treatment implementation is available, which is purely baes on
 * fields. We hope to get rid of this at some point, therefore clients should NOT use these methods.
 * <p>
 * Currently, this interface provides conversion methods from/to double. This is required for the current implementation
 * of unit conversions. The general plan for the future is to also base the unit conversions fully on the field
 * structure, therefore, these two methods are marked as deprecated, to avoid that clients start rely on them and abuse
 * them.
 * 
 * @author kfuchsbe
 * @param <T>
 */
public interface Cheating<T> {

    /**
     * Has to return a field element, constructed from the given double value.
     * 
     * @param value the double value from which to construct the field element.
     * @return the field element constructed from the double value
     */
    T fromDouble(double value);

    /**
     * Has to convert the given field element to a (unique) double value.
     * 
     * @param value field element to convert
     * @return a double value related to the field
     */
    double toDouble(T value);
}
