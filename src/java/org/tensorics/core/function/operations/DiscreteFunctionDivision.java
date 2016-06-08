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

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.lang.FunctionSupportWithConversion;

import com.google.common.base.Function;

/**
 * The {@link DiscreteFunctionBinaryOperation} that describes the division of two {@link DiscreteFunction}s
 * 
 * @author caguiler
 * @param <X> the type of the independent variable in the {@link DiscreteFunction}.
 * @param <Y> the type of the dependent variable in the {@link DiscreteFunction}
 */
public class DiscreteFunctionDivision<X extends Comparable<? super X>, Y> extends FunctionSupportWithConversion<X, Y>
        implements DiscreteFunctionBinaryOperation<X, Y> {

    public DiscreteFunctionDivision(Environment<Y> environment, Function<X, Y> conversion) {
        super(environment, conversion);
    }

    @Override
    public DiscreteFunction<X, Y> perform(DiscreteFunction<X, Y> left, DiscreteFunction<X, Y> right) {
        return calculate(left).dividedBy(right);
    }
}
