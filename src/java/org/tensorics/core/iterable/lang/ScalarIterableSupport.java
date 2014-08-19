/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.lang;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * Provides utility methods for acting on collections of field elements.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the field
 */
public class ScalarIterableSupport<V> extends ScalarSupport<V> {

    private final IterableOperationRepository<V> repository;

    public ScalarIterableSupport(ExtendedField<V> field) {
        super(field);
        this.repository = new IterableOperationRepository<>(field);
    }

    public final V avarageOf(Iterable<V> values) {
        return repository.average().perform(values);
    }

    public final V sizeOf(Iterable<V> values) {
        return repository.size().perform(values);
    }

    public final V sumOf(Iterable<V> values) {
        return repository.sum().perform(values);
    }

    public V rmsOf(Iterable<V> values) {
        return repository.rms().perform(values);
    }

    public V sumOfSquaresOf(Iterable<V> values) {
        return repository.sumOfSquares().perform(values);
    }

}
