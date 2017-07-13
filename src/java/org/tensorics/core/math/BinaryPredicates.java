// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

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
     * logically negated value of the original. For example, if the predicate is {@code '<='}, then this method will
     * transform the predicate {@code (a <= b) to !(a <= b)}, which is equivalent to {@code (a > b)}.
     *
     * @param original the original predicate.
     * @return
     */
    public static <T> BinaryPredicate<T> not(BinaryPredicate<T> original) {
        return new NegatedBinaryPredicateView<>(original);
    }

    /**
     * Inverts the given binary predicate. Thus, if e.g. the predicate is {@code '<='}, then this method will transform
     * the predicate {@code (a <= b)} to {@code (a >= b)}.
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

}
