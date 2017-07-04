// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.quantity;

import static com.google.common.base.Preconditions.checkArgument;

import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Groups a value together with its unit. Additionally an error and a validity
 * flag can be provided. If the latter two are not present explicitely given,
 * then the validity will be {@code true} and the (optional) error will not be
 * present.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the (numerical) scalar values.
 */
public final class ImmutableQuantifiedValue<V> implements QuantifiedValue<V> {
	private static final long serialVersionUID = 1L;

	private final V value; // NOSONAR
	private final Unit unit; // NOSONAR
	private final boolean validity; // NOSONAR
	private final Optional<V> error; // NOSONAR

	private ImmutableQuantifiedValue(V value, Unit unit, boolean validity, Optional<V> error) { // NOSONAR
		this.value = value;
		this.unit = unit;
		this.validity = validity;
		this.error = error;
	}

	private ImmutableQuantifiedValue(V value, Unit unit) {
		this(value, unit, true, Optional.<V>absent());
	}

	public static <V> ImmutableQuantifiedValue<V> of(V value, Unit unit) {
		checkArgument(value != null, "Argument 'value' must not be null!");
		checkArgument(unit != null, "Argument 'unit' must not be null!");
		return new ImmutableQuantifiedValue<>(value, unit);
	}

	public ImmutableQuantifiedValue<V> withValidity(boolean newValidity) {
		return new ImmutableQuantifiedValue<>(value(), unit(), newValidity, error());
	}

	public ImmutableQuantifiedValue<V> withError(Optional<V> newError) {
		checkArgument(newError != null, "Argument 'error' must not be null!");
		return new ImmutableQuantifiedValue<>(value(), unit(), validity(), newError);
	}

	public ImmutableQuantifiedValue<V> withError(V newError) {
		checkArgument(newError != null, "Argument 'error' must not be null!");
		return withError(Optional.of(newError));
	}

	@Override
	public V value() {
		return this.value;
	}

	@Override
	public Unit unit() {
		return this.unit;
	}

	@Override
	public Boolean validity() {
		return this.validity;
	}

	@Override
	public Optional<V> error() {
		return this.error;
	}

	@Override
	public String toString() {
		return value + errorToString() + " " + unit + " " + validityToString();
	}

	private String validityToString() {
		if (validity) {
			return "";
		}
		return "[INVALID]";
	}

	private String errorToString() {
		if (error.isPresent()) {
			return "±" + error.get();
		}
		return "";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ImmutableQuantifiedValue<?> other = (ImmutableQuantifiedValue<?>) obj;
		if (error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!error.equals(other.error)) {
			return false;
		}
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		if (validity != other.validity) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + (validity ? 1231 : 1237); // NOSONAR
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

}
