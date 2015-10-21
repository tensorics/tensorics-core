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

package org.tensorics.incubate.function;

import com.google.common.base.Preconditions;

/**
 * A continuous function, generated from a discrete one together with an appropriate interpolation strategy.
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable (in)
 * @param <Y> the type of the dependent variable (out)
 */
public final class InterpolatedFunction<X extends Comparable<X>, Y> implements ContinuousFunction<X, Y> {

    private final DiscreteFunction<X, Y> discreteFunction;
    private final InterpolationStrategy<X, Y> interpolationStrategy;

    private InterpolatedFunction(DiscreteFunction<X, Y> function, InterpolationStrategy<X, Y> strategy) {
        this.interpolationStrategy = strategy;
        this.discreteFunction = function;
        Preconditions
                .checkArgument(discreteFunction != null, "Argument '" + "DiscreteFunction" + "' must not be null!");
        Preconditions.checkArgument(interpolationStrategy != null, "Argument '" + "InterpolationStrategy"
                + "' must not be null!");
    }

    @Override
    public Y getY(X xValue) {
        return interpolationStrategy.interpolate(xValue, discreteFunction);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static <X extends Comparable<X>, Y> InterpolatedFunction<X, Y> of(DiscreteFunction<X, Y> function,
            InterpolationStrategy<X, Y> strategy) {
        return new InterpolatedFunction<>(function, strategy);
    }

}
