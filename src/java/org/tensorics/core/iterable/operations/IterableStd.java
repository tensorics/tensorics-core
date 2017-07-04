/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.operations;

import static com.google.common.collect.Iterables.isEmpty;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation that takes and iterable of a certain type of values (for which a
 * field has to be provided) and calculates the standard deviation out of it.
 * <p>
 * For the definition of the standard deviation, have a look at
 * <a href="https://en.wikipedia.org/wiki/Standard_deviation">wikipedia</a>.
 * 
 * @author caguiler
 * @param <V>
 *            the type of the scalars (elements of the field on which the
 *            standard deviation will be based)
 */
public class IterableStd<V> extends ScalarSupport<V> implements IterableOperation<V> {

	private final IterableVar<V> iterableVar;
	private final ExtendedField<V> field;

	public IterableStd(ExtendedField<V> field) {
		super(field);
		this.field = field;
		this.iterableVar = new IterableVar<>(field);
	}

	@Override
	public V apply(Iterable<V> values) {
		if (isEmpty(values)) {
			throw new IllegalArgumentException("standard deviation of empty value set is not possible.");
		}

		return calculate(iterableVar.apply(values)).root(field.two());
	}

}
