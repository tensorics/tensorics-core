/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

import org.tensorics.core.math.Math;

/**
 * Provides mathematical operations on doubles. This is of course the most trivial implementation of the {@link Math}
 * interface, since it simply delegates to the {@link java.lang.Math} class. However, this is necessary to keep the the
 * tensorics language generic for any scalar values.
 * 
 * @author kfuchsbe
 */
public class DoubleMath implements Math<Double> {

    @Override
    public Double pow(Double base, Double exponent) {
        return java.lang.Math.pow(base, exponent);
    }

}
