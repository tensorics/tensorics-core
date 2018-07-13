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

package org.tensorics.core.iterable.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tree.domain.Expression;

/**
 * An unresolved expression which uses an operation on iterables to produce one value of the same type. An instance will
 * contain the operation to act on the iterable as well as an expression for the iterable on which the operation will
 * have to be performed.
 *
 * @author kfuchsbe
 * @param <T> the type of the elements of the iterable and the result of this expression.
 */
public class IterableOperationExpression<T> extends ConversionOperationExpression<Iterable<T>, T> {
    private static final long serialVersionUID = 1L;

    public IterableOperationExpression(Conversion<Iterable<T>, T> operation,
            Expression<? extends Iterable<T>> iterable) {
        super(operation, iterable);
    }

}
