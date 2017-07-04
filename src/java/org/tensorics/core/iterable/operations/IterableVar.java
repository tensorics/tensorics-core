/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import static com.google.common.collect.Iterables.isEmpty;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation that takes and iterable of a certain type of values (for which a
 * field has to be provided) and calculates the variance out of it.
 * <p>
 * For the definition of the variance, have a look at
 * <a href="https://en.wikipedia.org/wiki/Variance">wikipedia</a>.
 * 
 * @author caguiler
 * @param <V>
 *            the type of the scalars (elements of the field on which the
 *            variance will be based)
 */
public class IterableVar<V> extends ScalarSupport<V> implements IterableOperation<V> {

	private final IterableAverage<V> iterableAverage;
	private final ExtendedField<V> field;

	public IterableVar(ExtendedField<V> field) {
		super(field);
		this.field = field;
		this.iterableAverage = new IterableAverage<>(field);
	}

	@Override
	public V apply(Iterable<V> values) {
		if (isEmpty(values)) {
			throw new IllegalArgumentException("variance of empty value set is not possible.");
		}

		final V average = iterableAverage.apply(values);

		// @formatter:off
        Iterable<V> squaredDifferences = StreamSupport.stream(values.spliterator(), false)
           .map(v -> calculate(v).minus(average))
           .map(difference -> calculate(difference).toThePowerOf(field.two()))
          .collect(Collectors.toList());
        // @formatter:on

		return iterableAverage.apply(squaredDifferences);
	}
}
