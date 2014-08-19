/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * This operations takes an iterable of field elements as input and calculates the sume of them.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field.
 */
public class IterableSumOfSquares<V> extends ScalarSupport<V> implements IterableOperation<V> {

    public IterableSumOfSquares(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V perform(Iterable<V> iterable) {
        V sum = zero();
        for (V value : iterable) {
            sum = calculate(sum).plus(squareOf(value));
        }
        return sum;
    }

}
