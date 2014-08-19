/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

import static com.google.common.base.Preconditions.checkNotNull;

import org.tensorics.core.math.Cheating;

/**
 * The implementation of those special methods which are (still) necessary to convert field elements to doubles and vice
 * versa. Anyhow, the implementation for doubles it is trivial and does not harm.
 * 
 * @author kfuchsbe
 */
public class DoubleCheating implements Cheating<Double> {

    /**
     * Converts a double value to a field element (double).
     * 
     * @deprecated the whole class should not be necessary in the future, if the unit-conversions are based fully on
     *             field operations.
     */
    @Deprecated
    @Override
    public Double fromDouble(double value) {
        return value;
    }

    /**
     * Converts field element (double) to a double value.
     * 
     * @deprecated the whole class should not be necessary in the future, if the unit-conversions are based fully on
     *             field operations.
     */
    @Deprecated
    @Override
    public double toDouble(Double value) {
        return checkNotNull(value, "Value must not be null.");
    }

}
