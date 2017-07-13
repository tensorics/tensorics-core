/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

import java.io.Serializable;

import org.tensorics.core.math.predicates.BinaryPredicate;

final class NegatedBinaryPredicateView<T> implements BinaryPredicate<T>, Serializable {

    private static final long serialVersionUID = 1L;
    private final BinaryPredicate<T> original;

    NegatedBinaryPredicateView(BinaryPredicate<T> original) {
        this.original = original;
    }

    @Override
    public boolean test(T left, T right) {
        return !original.test(left, right);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((original == null) ? 0 : original.hashCode());
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
        NegatedBinaryPredicateView<?> other = (NegatedBinaryPredicateView<?>) obj;
        if (original == null) {
            if (other.original != null) {
                return false;
            }
        } else if (!original.equals(other.original)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "NegatedBinaryPredicateView [original=" + original + "]";
    }

}