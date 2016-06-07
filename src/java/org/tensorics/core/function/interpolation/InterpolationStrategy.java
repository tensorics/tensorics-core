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

package org.tensorics.core.function.interpolation;

import java.io.Serializable;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.function.DiscreteFunction;

import com.google.common.base.Function;

/**
 * A strategy defines how to calculate output values of a function from the a finite set of discrete values (from a
 * discrete function).
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable (in)
 * @param <Y> the type of the dependent variable (output)
 */
public interface InterpolationStrategy<Y> extends Serializable, ManipulationOption {

    <X extends Comparable<? super X>> Y interpolate(X xValue, DiscreteFunction<X, Y> function,
            Function<X, Y> conversion);

    @Override
    public default Class<? extends ManipulationOption> getMarkerInterface() {
        return InterpolationStrategy.class;
    }

}
