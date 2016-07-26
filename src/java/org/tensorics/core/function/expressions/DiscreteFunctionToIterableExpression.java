// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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
package org.tensorics.core.function.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

/**
 * Represents a deferred expression which takes expressions of {@link DiscreteFunction} and converts them into
 * expressions of iterable.
 * 
 * @author kfuchsbe, caguiler
 * @param <X> the type of the independent variable in the {@link DiscreteFunction}.
 * @param <Y> the type of the dependent variable in the {@link DiscreteFunction}
 */
public class DiscreteFunctionToIterableExpression<Y>
        extends ConversionOperationExpression<DiscreteFunction<?, Y>, Iterable<Y>> {

    public DiscreteFunctionToIterableExpression(Conversion<DiscreteFunction<?, Y>, Iterable<Y>> operation,
            Expression<DiscreteFunction<?, Y>> source) {
        super(operation, source);
    }
}
