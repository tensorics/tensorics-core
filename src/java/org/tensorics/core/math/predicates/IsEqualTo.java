/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.predicates;

public final class IsEqualTo<T> implements BinaryPredicate<T> {

    private static final IsEqualTo<?> INSTANCE = new IsEqualTo<>();

    @Override
    public Boolean perform(T left, T right) {
        return test(left, right);
    }

    @Override
    public boolean test(T left, T right) {
        return left.equals(right);
    }

    @SuppressWarnings("unchecked")
    public static final <T> IsEqualTo<T> isEqualTo() {
        return (IsEqualTo<T>) INSTANCE;
    }

}
