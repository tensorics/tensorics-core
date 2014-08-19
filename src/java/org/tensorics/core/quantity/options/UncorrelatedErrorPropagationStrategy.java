/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.scalar.lang.ScalarSupport;

import com.google.common.base.Optional;

/**
 * This error propagation assumes uncorrelated errors.
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Propagation_of_uncertainty">http://en .wikipedia.org/wiki/
 * Propagation_of_uncertainty</a>
 * 
 * @author kfuchsbe
 * @param <V>
 */
public class UncorrelatedErrorPropagationStrategy<V> extends ScalarSupport<V> implements ErrorPropagationStrategy<V> {

    public UncorrelatedErrorPropagationStrategy(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public Optional<V> errorForSumAndDifference(ErronousValue<V> left, ErronousValue<V> right) {
        if (left.error().isPresent() && right.error().isPresent()) {
            V first = squareOf(left.error().get());
            V second = squareOf(right.error().get());
            return Optional.of(squareRootOf(calculate(first).plus(second)));
        }
        return Optional.<V> absent();
    }

    @Override
    public Optional<V> errorForProductAndDivision(ErronousValue<V> left, ErronousValue<V> right) {
        if (left.error().isPresent() && right.error().isPresent()) {
            V first = squaredValueErrorProduct(left, right);
            V second = squaredValueErrorProduct(right, left);
            return Optional.of(squareRootOf(calculate(first).plus(second)));
        }
        return Optional.<V> absent();
    }

    private V squaredValueErrorProduct(ErronousValue<V> left, ErronousValue<V> right) {
        return calculate(squareOf(left.value())).times(squareOf(right.error().get()));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<ErrorPropagationStrategy> getMarkerInterface() {
        return ErrorPropagationStrategy.class;
    }

}
