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

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.expressions.DiscreteFunctionToIterableExpression;
import org.tensorics.core.function.operations.CodomainExtraction;
import org.tensorics.core.iterable.lang.ScalarIterableExpressionSupport;
import org.tensorics.core.tree.domain.Expression;

/**
 * Provides utility methods for acting on expressions of {@link DiscreteFunction}s from X to Y.
 * <p>
 * The type of the values of the discrete function codomain is also the type of the values of the field.
 * 
 * @author caguiler
 * @param <X> the type of the independent variable (input) of the discrete function
 * @param <Y> the type of the dependent variable (output) of the discrete function and the type of the scalar values
 *            (elements of the field) on which to operate
 */
public class FunctionExpressionSupportWithConversion<X, Y> extends ScalarIterableExpressionSupport<Y> {

    private final Environment<Y> environment;
    private final CodomainExtraction<X, Y> toYValues;
    private final Conversion<X, Y> conversion;

    FunctionExpressionSupportWithConversion(Environment<Y> environment, Conversion<X, Y> conversion) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
        toYValues = new CodomainExtraction<>();
    }

    public <Z extends Comparable<? super Z>> OngoingDeferredDiscreteFunctionOperation<Z, Y> calculateF(
            Expression<DiscreteFunction<Z, Y>> expression) {
        try {
            @SuppressWarnings("unchecked")
            Conversion<Z, Y> conversionFromComparabletoY = (Conversion<Z, Y>) conversion;
            return new OngoingDeferredDiscreteFunctionOperation<>(environment, expression, conversionFromComparabletoY);
        } catch (ClassCastException castException) {
            throw new IllegalStateException(
                    "In order to use the method \"calculate\" the X type of FunctionExpressionSupportWithConversion<X,Y> MUST implement Comparable interface",
                    castException);
        }
    }

    public final Expression<Y> averageOfF(Expression<DiscreteFunction<X, Y>> function) {
        return averageOf(yValuesAsIterableExpression(function));
    }

    public final Expression<Y> rmsOfF(Expression<DiscreteFunction<X, Y>> function) {
        return rmsOf(yValuesAsIterableExpression(function));
    }

    private Expression<Iterable<Y>> yValuesAsIterableExpression(Expression<DiscreteFunction<X, Y>> function) {
        return new DiscreteFunctionToIterableExpression<>(toYValues, function);
    }

    /**
     * Only used by subclasses
     * 
     * @return the {@link Environment} of this {@link FunctionSupportWithConversion}
     */
    protected Environment<Y> environment() {
        return environment;
    }
}
