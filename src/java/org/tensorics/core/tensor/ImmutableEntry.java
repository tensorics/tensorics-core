/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import java.io.Serializable;

/**
 * @author kfuchsbe
 * @param <T> The type of the value of the tensor entry
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class ImmutableEntry<T> implements Tensor.Entry<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private final T value;
    private final Position position;

    public ImmutableEntry(Position position, T value) {
        this.value = value;
        this.position = position;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ImmutableEntry [value=" + value + ", coordinates=" + position.getCoordinates() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @SuppressWarnings({ "rawtypes", "PMD.CyclomaticComplexity" })
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
        ImmutableEntry other = (ImmutableEntry) obj;
        if (position == null) {
            if (other.position != null) {
                return false;
            }
        } else if (!position.equals(other.position)) {
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
    public Position getPosition() {
        return this.position;
    }

}
