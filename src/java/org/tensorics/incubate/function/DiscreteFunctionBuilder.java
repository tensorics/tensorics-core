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

/**
 * A builder for discrete functions. It provides methods to put new values and to decide on the interpolation strategies
 * to use.
 * 
 * @author kfuchsbe
 * @param <X> the type of the values along the X-axis
 * @param <Y> the type of the values along the Y-axis
 */
public interface DiscreteFunctionBuilder<X extends Comparable<X>, Y> {

    DiscreteFunctionBuilder<X, Y> put(X key, Y value);

    DiscreteFunctionBuilder<X, Y> put(X key, Y value, Y error);

    DiscreteFunctionBuilder<X, Y> withInterpolationStrategy(InterpolationStrategy<X, Y> interpolationStrategy);

    DiscreteFunctionBuilder<X, Y> withName(String name);

    DiscreteFunction<X, Y> build();

}