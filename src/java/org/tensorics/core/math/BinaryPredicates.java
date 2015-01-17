/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math;

import org.tensorics.core.math.predicates.BinaryPredicate;

/**
 * Provides utility methods for binary predicates
 * 
 * @author kfuchsbe
 */
public final class BinaryPredicates {

    private BinaryPredicates() {
        /* only static methods */
    }

    /**
     * Negates the given binary predicate. Thus, the {@link BinaryPredicate#test(Object, Object)} will return the
     * logically negated value of the original. For example, if the predicate is '<=', then this method will transform
     * the predicate (a <= b) to !(a <= b), which is equivalent to (a > b).
     * 
     * @param original the original predicate.
     * @return
     */
    public static <T> BinaryPredicate<T> not(BinaryPredicate<T> original) {
        return new NegatedBinaryPredicateView<>(original);
    }

    /**
     * Inverts the given binary predicate. Thus, if e.g. the predicate is '<=', then this method will transform the
     * predicate (a <= b) to (a >= b).
     * 
     * @param original the original predicate.
     * @return the inverted predicate.
     */
    public static <T> BinaryPredicate<T> invert(BinaryPredicate<T> original) {
        return new InvertedBinaryPredicateView<>(original);
    }

    /**
     * Returns a logical 'and' combination of the two binary predicates. Thus, the resulting predicate will only be
     * fulfilled as soon as both predicate test methods return true.
     * 
     * @param leftPredicate the first predicate which shall be part of the and condition
     * @param rightPredicate the second predicate which shall be part of the and condition
     * @return a predicate which represents a logical 'AND' between the two predicates
     */
    public static <T> BinaryPredicate<T> and(BinaryPredicate<T> leftPredicate, BinaryPredicate<T> rightPredicate) {
        return new AndBinaryPredicateView<>(leftPredicate, rightPredicate);
    }

    private static final class NegatedBinaryPredicateView<T> implements BinaryPredicate<T> {

        private final BinaryPredicate<T> original;

        NegatedBinaryPredicateView(BinaryPredicate<T> original) {
            this.original = original;
        }

        @Override
        public boolean test(T left, T right) {
            return !original.test(left, right);
        }

    }

    private static final class InvertedBinaryPredicateView<T> implements BinaryPredicate<T> {

        private final BinaryPredicate<T> original;

        InvertedBinaryPredicateView(BinaryPredicate<T> original) {
            this.original = original;
        }

        @Override
        public boolean test(T left, T right) {
            return original.test(right, left);
        }

    }

    private static final class AndBinaryPredicateView<T> implements BinaryPredicate<T> {

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

    }

}
