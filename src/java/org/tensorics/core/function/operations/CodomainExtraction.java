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
package org.tensorics.core.function.operations;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MathFunctions;

/**
 * A conversion that takes a {@link DiscreteFunction} and produces an iterable that contains the values of its codomain.
 * 
 * @author caguiler
 * @param <X> the type of the independent variable (input) of the discrete function
 * @param <Y> the type of the dependent variable (output)of the discrete function
 * @see https://en.wikipedia.org/wiki/Codomain
 */
public class CodomainExtraction<X, Y> implements Conversion<DiscreteFunction<X, Y>, Iterable<Y>> {

    @Override
    public Iterable<Y> apply(DiscreteFunction<X, Y> inputfunction) {
        return MathFunctions.yValuesOf(inputfunction);
    }
}