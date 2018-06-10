/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

public class UnresolvedBinaryOperation<T> extends AbstractDeferredExpression<BinaryOperation<T>> {
    private static final long serialVersionUID = 1L;

    private final Class<? extends BinaryOperation<? super T>> operationType;

    private UnresolvedBinaryOperation(Class<? extends BinaryOperation<? super T>> operationType) {
        this.operationType = requireNonNull(operationType, "operationType must not be null");
    }

    public static <T> UnresolvedBinaryOperation<T> of(Class<? extends BinaryOperation<? super T>> operationType) {
        return new UnresolvedBinaryOperation<T>(operationType);
    }

    public Class<? extends BinaryOperation<? super T>> operationType() {
        return this.operationType;
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "UnresolvedBinaryOperation [operationType=" + operationType + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
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
        UnresolvedBinaryOperation<?> other = (UnresolvedBinaryOperation<?>) obj;
        if (operationType == null) {
            if (other.operationType != null) {
                return false;
            }
        } else if (!operationType.equals(other.operationType)) {
            return false;
        }
        return true;
    }

}
