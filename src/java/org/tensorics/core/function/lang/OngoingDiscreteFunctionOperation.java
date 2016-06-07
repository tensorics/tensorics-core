/*******************************************************************************
 * This file is part of tensorics. Copyright (c) 2008-2016, CERN. All rights reserved. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.InterpolatedFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.function.MathFunctions;
import org.tensorics.core.function.interpolation.InterpolationStrategy;
import org.tensorics.core.math.operations.BinaryOperation;

import com.google.common.base.Function;
import com.google.common.collect.Sets;

public class OngoingDiscreteFunctionOperation<X extends Comparable<? super X>, Y> {

    private final Environment<Y> environment;
    private Function<X, Y> conversion;
    private final DiscreteFunction<X, Y> left;

    public OngoingDiscreteFunctionOperation(Environment<Y> environment, DiscreteFunction<X, Y> left,
            Function<X, Y> conversion) {
        this.environment = environment;
        this.conversion = conversion;
        this.left = left;
    }

    public DiscreteFunction<X, Y> plus(DiscreteFunction<X, Y> right) {
        return applyOperation(right, environment.field().addition());
    }

    public DiscreteFunction<X, Y> minus(DiscreteFunction<X, Y> right) {
        return applyOperation(right, environment.field().subtraction());
    }

    public DiscreteFunction<X, Y> times(DiscreteFunction<X, Y> right) {
        return applyOperation(right, environment.field().multiplication());
    }

    public DiscreteFunction<X, Y> dividedBy(DiscreteFunction<X, Y> right) {
        return applyOperation(right, environment.field().division());
    }

    private DiscreteFunction<X, Y> applyOperation(DiscreteFunction<X, Y> right, BinaryOperation<Y> operation) {
        @SuppressWarnings("unchecked")
        InterpolationStrategy<Y> strategy = environment.options().get(InterpolationStrategy.class);

        InterpolatedFunction<X, Y> rigthInterpolated = MathFunctions.interpolated(right, strategy, conversion);
        InterpolatedFunction<X, Y> leftInterpolated = MathFunctions.interpolated(left, strategy, conversion);

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
