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

import static org.tensorics.core.function.MathFunctions.yValuesOf;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.iterable.lang.ScalarIterableSupport;

/**
 * Provides utility methods for acting on {@link DiscreteFunction}s from X to Y.
 * <p>
 * The type of the values of the discrete function codomain is also the type of the values of the field.
 * 
 * @author caguiler
 * @param <X> the type of the independent variable (input) of the discrete function
 * @param <Y> the type of the dependent variable (output) of the discrete function and the type of the scalar values
 *            (elements of the field) on which to operate
 */
public class FunctionSupportWithConversion<X, Y> extends ScalarIterableSupport<Y> {

    private Environment<Y> environment;
    private Conversion<X, Y> conversion;

    /**
     * @param environment the {@link Environment} for this support
     * @param conversion defines how to transform a value of X type to Y type
     */
    FunctionSupportWithConversion(Environment<Y> environment, Conversion<X, Y> conversion) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
    }

    public <Z extends Comparable<? super Z>> OngoingDiscreteFunctionBinaryOperation<Z, Y> calculate(
            DiscreteFunction<Z, Y> left) {
        try {
            @SuppressWarnings("unchecked")
            Conversion<Z, Y> conversionFromComparabletoY = (Conversion<Z, Y>) conversion;
            return new OngoingDiscreteFunctionBinaryOperation<>(environment, left, conversionFromComparabletoY);
        } catch (ClassCastException castException) {
            throw new IllegalStateException(
                    "In order to use the method \"calculate\" the X type of FunctionSupportWithConversion<X,Y> MUST implement comparable",
                    castException);
        }
    }

    /**
     * @return average of the y-values of a {@link DiscreteFunction}
     */
    public final Y averageOf(DiscreteFunction<X, Y> function) {
        return avarageOf(yValuesOf(function));
    }

    /**
     * @return root mean square of the y-values of a {@link DiscreteFunction}
     */
    public Y rmsOf(DiscreteFunction<X, Y> function) {
        return rmsOf(yValuesOf(function));
    }

    /**
     * Only used by subclasses
     * 
     * @return the {@link Environment} of this {@link FunctionSupport}
     */
    protected Environment<Y> environment() {
        return environment;
    }
}
