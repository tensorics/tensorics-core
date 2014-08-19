/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import static com.google.common.collect.Iterables.isEmpty;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation that takes and iterable of a certain type of values (for which a field has to be provided) and
 * calculates the rms (Root mean square) out of it.
 * <p>
 * For the definition of the root mean square, have a look at <a
 * href="http://en.wikipedia.org/wiki/Root_mean_square">wikipedia</a>.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalars (elements of the field on which the rms will be based)
 */
public class IterableRms<V> extends ScalarSupport<V> implements IterableOperation<V> {

    private final IterableSumOfSquares<V> sumOfSquares;
    private final IterableSize<V> size;

    public IterableRms(ExtendedField<V> field) {
        super(field);
        this.sumOfSquares = new IterableSumOfSquares<>(field);
        this.size = new IterableSize<>(field);
    }

    @Override
    public V perform(Iterable<V> values) {
        if (isEmpty(values)) {
            throw new IllegalArgumentException("r.m.s. of empty value set is not possible.");
        }
        return squareRootOf(calculate(sumOfSquares.perform(values)).dividedBy(size.perform(values)));

    }

}
