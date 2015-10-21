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

package org.tensorics.core.math.predicates;

/**
 * Represents a predicate (boolean-valued function) of two arguments of the same type. This is a functional interface
 * whose functional method is {@link #test(Object, Object)}.
 * 
 * @author kfuchsbe
 * @param <T> the type of the operands
 */
public interface BinaryPredicate<T> {

    /**
     * Evaluates the predicate (condition) on the given arguments.
     * 
     * @param left the left operator of the condition
     * @param right the right operator of the condition
     * @return {@code true} if the predicate is fulfilled, {@code false} otherwise.
     */
    boolean test(T left, T right);

}
