/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

import java.io.Serializable;

import org.tensorics.core.math.operations.CommutativeAssociativeOperation;
import org.tensorics.core.math.operations.UnaryOperation;

public class AbstractAbelianGroup<E> implements AbelianGroup<E>, Serializable {
    private static final long serialVersionUID = 1L;

    private final CommutativeAssociativeOperation<E> operation;
    private final E identity;
    private final UnaryOperation<E> inversion;

    public AbstractAbelianGroup(CommutativeAssociativeOperation<E> operation, E identity, UnaryOperation<E> inversion) {
        this.operation = operation;
        this.identity = identity;
        this.inversion = inversion;
    }

    @Override
    public CommutativeAssociativeOperation<E> operation() {
        return this.operation;
    }

    @Override
    public E identity() {
        return this.identity;
    }

    @Override
    public UnaryOperation<E> inversion() {
        return this.inversion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((identity == null) ? 0 : identity.hashCode());
        result = prime * result + ((inversion == null) ? 0 : inversion.hashCode());
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        return result;
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
        AbstractAbelianGroup<?> other = (AbstractAbelianGroup<?>) obj;
        if (identity == null) {
            if (other.identity != null) {
                return false;
            }
        } else if (!identity.equals(other.identity)) {
            return false;
        }
        if (inversion == null) {
            if (other.inversion != null) {
                return false;
            }
        } else if (!inversion.equals(other.inversion)) {
            return false;
        }
        if (operation == null) {
            if (other.operation != null) {
                return false;
            }
        } else if (!operation.equals(other.operation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractAbelianGroup [operation=" + operation + ", identity=" + identity + ", inversion=" + inversion
                + "]";
    }

}
