/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

import java.io.Serializable;

import org.tensorics.core.math.predicates.BinaryPredicate;

final class AndBinaryPredicateView<T> implements BinaryPredicate<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private final BinaryPredicate<T> leftPredicate;
    private final BinaryPredicate<T> rightPredicate;

    public AndBinaryPredicateView(BinaryPredicate<T> left, BinaryPredicate<T> right) {
        this.leftPredicate = left;
        this.rightPredicate = right;
    }

    @Override
    public boolean test(T left, T right) {
        return (leftPredicate.test(left, right) && rightPredicate.test(left, right));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((leftPredicate == null) ? 0 : leftPredicate.hashCode());
        result = prime * result + ((rightPredicate == null) ? 0 : rightPredicate.hashCode());
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
        AndBinaryPredicateView<?> other = (AndBinaryPredicateView<?>) obj;
        if (leftPredicate == null) {
            if (other.leftPredicate != null) {
                return false;
            }
        } else if (!leftPredicate.equals(other.leftPredicate)) {
            return false;
        }
        if (rightPredicate == null) {
            if (other.rightPredicate != null) {
                return false;
            }
        } else if (!rightPredicate.equals(other.rightPredicate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AndBinaryPredicateView [leftPredicate=" + leftPredicate + ", rightPredicate=" + rightPredicate + "]";
    }

}