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
package org.tensorics.core.function;

import java.util.Comparator;
import java.util.Set;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.interpolation.InterpolationStrategy;

import com.google.common.base.Preconditions;

/**
 * Encodes a {@link InterpolatedFunction} as a combination of a {@link DiscreteFunction} plus an
 * {@link InterpolationStrategy}.
 * <p>
 * The obtaining of y-values associated with x-values not defined by the {@link DiscreteFunction} is delegated to the
 * {@link InterpolationStrategy}.
 * 
 * @author caguiler
 * @param <X> the type of the independent variable must be comparable. Otherwise is not possible to interpolate.
 * @see InterpolatedFunction
 */
public class DefaultInterpolatedFunction<X, Y> implements InterpolatedFunction<X, Y> {

    private final DiscreteFunction<X, Y> backingFunction;
    private final InterpolationStrategy<Y> strategy;
    private final Conversion<X, Y> conversion;
    private Comparator<X> comparator;

    public DefaultInterpolatedFunction(DiscreteFunction<X, Y> function, InterpolationStrategy<Y> strategy,
            Conversion<X, Y> conversion, Comparator<X> comparator) {
        this.backingFunction = Preconditions.checkNotNull(function, "function cannot be null");
        this.strategy = Preconditions.checkNotNull(strategy, "strategy cannot be null");
        this.conversion = Preconditions.checkNotNull(conversion, "conversion cannot be null");
        this.comparator = Preconditions.checkNotNull(comparator, "comparator cannot be null");
    }

    @Override
    public Y apply(X input) {
        if (definedXValues().contains(input)) {
            return backingFunction.apply(input);
        }
        return strategy.interpolate(input, backingFunction, conversion, comparator);
    }

    @Override
    public Set<X> definedXValues() {
        return backingFunction.definedXValues();
    }
}
