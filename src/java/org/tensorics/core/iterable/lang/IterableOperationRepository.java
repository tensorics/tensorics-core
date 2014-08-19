/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.lang;

import org.tensorics.core.iterable.operations.IterableAverage;
import org.tensorics.core.iterable.operations.IterableRms;
import org.tensorics.core.iterable.operations.IterableSize;
import org.tensorics.core.iterable.operations.IterableSum;
import org.tensorics.core.iterable.operations.IterableSumOfSquares;
import org.tensorics.core.math.ExtendedField;

/**
 * Contains instances of operations on iterables, based on a field. The main purpose is to be able to re-use the
 * instances of the operations, in order to avoid to have to re-create them all the time.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which the operations are based.
 */
public class IterableOperationRepository<V> {

    private final IterableAverage<V> iterableAverage;
    private final IterableSize<V> iterableSize;
    private final IterableSum<V> iterableSum;
    private final IterableRms<V> iterableRms;
    private final IterableSumOfSquares<V> iterableSumOfSquares;

    public IterableOperationRepository(ExtendedField<V> field) {
        iterableAverage = new IterableAverage<>(field);
        iterableSize = new IterableSize<>(field);
        iterableSum = new IterableSum<>(field);
        iterableRms = new IterableRms<>(field);
        iterableSumOfSquares = new IterableSumOfSquares<>(field);
    }

    public IterableAverage<V> average() {
        return iterableAverage;
    }

    public IterableSize<V> size() {
        return iterableSize;
    }

    public IterableSum<V> sum() {
        return iterableSum;
    }

    public IterableRms<V> rms() {
        return iterableRms;
    }

    public IterableSumOfSquares<V> sumOfSquares() {
        return iterableSumOfSquares;
    }

}