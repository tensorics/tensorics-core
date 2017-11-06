/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.predicates;

public class IsNotEqualTo<T> implements BinaryPredicate<T> {

    private static final IsNotEqualTo<?> INSTANCE = new IsNotEqualTo<>();

    @Override
    public Boolean perform(T left, T right) {
        return test(left, right);
    }

    @Override
    public boolean test(T left, T right) {
        return !left.equals(right);
    }

    @SuppressWarnings("unchecked")
    public static final <T> IsNotEqualTo<T> isNotEqualTo() {
        return (IsNotEqualTo<T>) INSTANCE;
    }
}
