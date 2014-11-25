/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.hamcrest;

import org.hamcrest.Matcher;
import org.tensorics.core.tensor.Tensor;

/**
 * Provides factory methods for matchers for tensoric objects.
 * 
 * @author kfuchsbe
 */
public final class TensoricsMatchers {

    private TensoricsMatchers() {
        /* Only static methods */
    }

    public static <V> Matcher<Tensor<V>> allElementsEqualTo(V value) {
        return AllElementsEqualTo.allElementsEqualTo(value);
    }

}
