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

package org.tensorics.core.math;

import java.util.Comparator;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.predicates.BinaryPredicate;

/**
 * A more explicit view on the algebraic structure of a field. It provides more dedicated operations.
 * 
 * @param <T> the type of the elements of the field
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface ExplicitField<T> {

    /**
     * Has to return the '+' operation.
     * 
     * @return the operation which can perform a + b.
     */
    BinaryOperation<T> addition();

    UnaryOperation<T> additiveInversion();

    BinaryOperation<T> subtraction();

    T zero();

    /**
     * Has to return the '*' operation.
     * 
     * @return the operation which can perform a * b.
     */
    BinaryOperation<T> multiplication();

    BinaryOperation<T> division();

    UnaryOperation<T> multiplicativeInversion();

    T one();

    T two();

    BinaryPredicate<T> less();

    BinaryPredicate<T> lessOrEqual();

    BinaryPredicate<T> equal();

    BinaryPredicate<T> greaterOrEqual();

    BinaryPredicate<T> greater();

    Comparator<T> comparator();
}