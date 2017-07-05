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

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * An immutable object that guarantees to have a value and an error.
 * 
 * @author kfuchsbe
 * @param <V> the type of the value and the error
 */
public final class ImmutableErronousValue<V> implements ErronousValue<V> {
    private static final long serialVersionUID = 1L;

    private final V value;

    private final Optional<V> error;

    private ImmutableErronousValue(V value, Optional<V> error) {
        Preconditions.checkArgument(value != null, "Argument '" + "value" + "' must not be null!");
        Preconditions.checkArgument(error != null, "Argument '" + "error" + "' must not be null!");
        this.value = value;
        this.error = error;
    }

    public static <V> ImmutableErronousValue<V> ofValue(V value) {
        return new ImmutableErronousValue<V>(value, Optional.<V> absent());
    }

    public static <V> ImmutableErronousValue<V> ofValueAndError(V value, V error) {
        return new ImmutableErronousValue<V>(value, Optional.of(error));
    }

    public static <V> ImmutableErronousValue<V> ofValueAndError(V value, Optional<V> error) {
        return new ImmutableErronousValue<V>(value, error);
    }

    @Override
    public V value() {
        return this.value;
    }

    @Override
    public Optional<V> error() {
        return this.error;
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
        ImmutableErronousValue<?> other = (ImmutableErronousValue<?>) obj;
        if (error == null) {
            if (other.error != null) {
                return false;
            }
        } else if (!error.equals(other.error)) {
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
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return value + "±" + error;
    }

}
