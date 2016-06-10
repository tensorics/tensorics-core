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

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver for expressions that convert certain type of objects into others.
 * 
 * @author kfuchsbe
 * @param <T> the type of the objects to be converted
 * @param <R> the type of the objects that result from the conversion
 */
public class ConversionOperationResolver<T, R> extends AbstractResolver<R, ConversionOperationExpression<T, R>> {

    @Override
    public boolean canResolve(ConversionOperationExpression<T, R> expression, ResolvingContext context) {
        return context.resolves(expression.getSource());
    }

    @Override
    public R resolve(ConversionOperationExpression<T, R> expression, ResolvingContext context) {
        T source = context.resolvedValueOf(expression.getSource());
        return expression.getOperation().apply(source);
    }

}
