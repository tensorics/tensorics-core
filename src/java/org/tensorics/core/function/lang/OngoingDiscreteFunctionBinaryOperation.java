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
import org.tensorics.core.function.operations.DiscreteFunctionOperationRepository;

/**
 * Provides methods to describe the right hand side of binary operations on {@link DiscreteFunction} from X to Y
 * 
 * @param <X> the type of the independent variable (input) of the discrete function
 * @param <Y> the type of the dependent variable (output) of the discrete function and the type of the scalar values
 *            (elements of the field) on which to operate
 * @author caguiler
 */
public class OngoingDiscreteFunctionBinaryOperation<X, Y> {

    private final DiscreteFunction<X, Y> left;
    private final DiscreteFunctionOperationRepository<X, Y> repository;

    /**
     * @param environment the {@link Environment} for this support
     * @param conversion defines how to transform a value of X type to Y type
     */
    OngoingDiscreteFunctionBinaryOperation(Environment<Y> environment, DiscreteFunction<X, Y> left,
            Conversion<X, Y> conversion, Comparator<X> comparator) {
        this.left = left;
        this.repository = new DiscreteFunctionOperationRepository<>(environment, conversion, comparator);
    }

    public final DiscreteFunction<X, Y> plus(DiscreteFunction<X, Y> right) {
        return repository.addition().perform(left, right);
    }

    public final DiscreteFunction<X, Y> minus(DiscreteFunction<X, Y> right) {
        return repository.subtraction().perform(left, right);
    }

    public final DiscreteFunction<X, Y> times(DiscreteFunction<X, Y> right) {
        return repository.multiplication().perform(left, right);
    }

    public final DiscreteFunction<X, Y> dividedBy(DiscreteFunction<X, Y> right) {
        return repository.division().perform(left, right);
    }
}
