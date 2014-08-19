/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * This operation takes an iterable of values as input and returns the sum of all the elements.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which the operation is based on.
 */
public class IterableSum<V> extends ScalarSupport<V> implements IterableOperation<V> {

    public IterableSum(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V perform(Iterable<V> iterable) {
        V sum = zero();
        for (V value : iterable) {
            sum = calculate(sum).plus(value);
        }
        return sum;
    }

}
