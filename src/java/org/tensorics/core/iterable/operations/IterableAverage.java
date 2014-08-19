/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import static com.google.common.collect.Iterables.isEmpty;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * This operation reduces an Iterable of a certain value type to one value by averaging over them.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values over which to average.
 */
public class IterableAverage<V> extends ScalarSupport<V> implements IterableOperation<V> {

    private final IterableSize<V> size;
    private final IterableSum<V> sum;

    public IterableAverage(ExtendedField<V> field) {
        super(field);
        this.size = new IterableSize<>(field);
        this.sum = new IterableSum<>(field);
    }

    @Override
    public V perform(Iterable<V> iterable) {
        if (isEmpty(iterable)) {
            throw new IllegalArgumentException("Averaging of empty value set is not possible.");
        }
        return calculate(sum.perform(iterable)).dividedBy(size.perform(iterable));
    }
}
