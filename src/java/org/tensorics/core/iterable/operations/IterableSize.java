/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation which counts the number of entries in an iterable and returns a scalar, corresponding to the count.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field.
 */
public class IterableSize<V> extends ScalarSupport<V> implements IterableOperation<V> {

    public IterableSize(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V perform(Iterable<V> iterable) {
        V one = one();
        V count = zero();
        for (@SuppressWarnings("unused")
        V value : iterable) {
            count = calculate(count).plus(one);
        }
        return count;
    }

}
