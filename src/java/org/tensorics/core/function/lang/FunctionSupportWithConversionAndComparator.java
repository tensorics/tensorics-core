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

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.operations.CodomainExtraction;
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
public class FunctionSupportWithConversionAndComparator<X, Y> extends ScalarIterableSupport<Y> {

    private final Environment<Y> environment;
    private final Conversion<X, Y> conversion;
    private CodomainExtraction<Y> codomainExtraction;
    private Comparator<X> comparator;

    /**
     * @param environment the {@link Environment} for this support
     * @param conversion defines how to transform a value of X type to Y type
     */
    FunctionSupportWithConversionAndComparator(Environment<Y> environment, Conversion<X, Y> conversion, Comparator<X> comparator) {
        super(environment.field());
        this.environment = environment;
        this.conversion = conversion;
        this.codomainExtraction = new CodomainExtraction<>();
        this.comparator = comparator;
    }

    public final OngoingDiscreteFunctionBinaryOperation<X, Y> calculate(DiscreteFunction<X, Y> left) {
        return new OngoingDiscreteFunctionBinaryOperation<>(environment, left, conversion, comparator);
    }

    /**
     * @return average of the y-values of a {@link DiscreteFunction}
     */
    public final <Z> Y averageOf(DiscreteFunction<Z, Y> function) {
        return averageOf(codomainExtraction.apply(function));
    }

    /**
     * @return root mean square of the y-values of a {@link DiscreteFunction}
     */
    public final <Z> Y rmsOf(DiscreteFunction<Z, Y> function) {
        return rmsOf(codomainExtraction.apply(function));
    }

    public final <Z> Y stdOf(DiscreteFunction<Z, Y> function) {
        return stdOf(codomainExtraction.apply(function));
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
