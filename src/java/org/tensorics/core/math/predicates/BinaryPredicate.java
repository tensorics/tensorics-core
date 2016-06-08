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

package org.tensorics.core.math.predicates;

import org.tensorics.core.math.operations.BinaryFunction;

/**
 * Represents a predicate (boolean-valued function) of two arguments of the same type. This is a functional interface
 * whose functional method is {@link #test(Object, Object)}.
 *
 * @param <T> the type of the operands
 * @author kfuchsbe
 */
public interface BinaryPredicate<T> extends BinaryFunction<T, Boolean> {

    /**
     * Evaluates the predicate (condition) on the given arguments.
     *
     * @param left  the left operator of the condition
     * @param right the right operator of the condition
     * @return {@code true} if the predicate is fulfilled, {@code false} otherwise.
     */
    boolean test(T left, T right);

    @Override
    default Boolean perform(T left, T right) {
        return test(left, right);
    }
}
