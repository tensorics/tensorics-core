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

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.InterpolatedFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.function.MathFunctions;
import org.tensorics.core.function.interpolation.InterpolationStrategy;
import org.tensorics.core.math.operations.BinaryOperation;

import com.google.common.collect.Sets;

/**
 * A binary operation that takes two {@link DiscreteFunction}s and produces a {@link DiscreteFunction}.
 * 
 * @author caguiler
 * @param <X> the type of the independent variable in the {@link DiscreteFunction}.
 * @param <Y> the type of the dependent variable in the {@link DiscreteFunction}
 */
public abstract class AbstractDiscreteFunctionBinaryOperation<X, Y> implements BinaryOperation<DiscreteFunction<X, Y>> {

    private final Conversion<X, Y> conversion;
    private final Environment<Y> environment;
    private final BinaryOperation<Y> operation;
    private final Comparator<X> comparator;

    AbstractDiscreteFunctionBinaryOperation(Environment<Y> environment, Conversion<X, Y> conversion,
            BinaryOperation<Y> operation, Comparator<X> comparator) {
        this.environment = environment;
        this.conversion = conversion;
        this.operation = operation;
        this.comparator = comparator;
    }

    @Override
    public DiscreteFunction<X, Y> perform(DiscreteFunction<X, Y> left, DiscreteFunction<X, Y> right) {
        @SuppressWarnings("unchecked")
        InterpolationStrategy<Y> strategy = environment.options().get(InterpolationStrategy.class);

        InterpolatedFunction<X, Y> rigthInterpolated = MathFunctions.interpolated(right, strategy, conversion,
                comparator);
        InterpolatedFunction<X, Y> leftInterpolated = MathFunctions.interpolated(left, strategy, conversion,
                comparator);

        MapBackedDiscreteFunction.Builder<X, Y> builder = MapBackedDiscreteFunction.builder();

        for (X x : Sets.union(left.definedXValues(), right.definedXValues())) {

            Y y1 = leftInterpolated.apply(x);
            Y y2 = rigthInterpolated.apply(x);

            Y result = operation.perform(y1, y2);

            builder.put(x, result);
        }

        return builder.build();
    }
}
