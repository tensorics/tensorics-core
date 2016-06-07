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

import com.google.common.base.Function;
import com.google.common.base.Functions;

/**
 * Provides starting methods for tensoric language expressions that operate on {@link DiscreteFunction}s.
 * 
 * @author caguiler
 * @param <Y> the type of the scalar values (elements of the field) on which to operate
 */
public class FunctionSupport<Y> {

    private final Environment<Y> environment;

    public FunctionSupport(Environment<Y> environment) {
        this.environment = environment;
    }

    public final <X extends Comparable<? super X>> FunctionSupportWithConversion<X, Y> withConversion(
            Function<X, Y> conversion) {
        return new FunctionSupportWithConversion<>(environment, conversion);
    }

    public final <Z extends Comparable<? super Z>> OngoingDiscreteFunctionOperation<Z, Z> calculate(
            DiscreteFunction<Z, Z> left) {
        FunctionSupport<Z> support = toSupportOfComparable();
        return new OngoingDiscreteFunctionOperation<>(support.environment, left, Functions.identity());
    }

    public final <Z extends Comparable<? super Z>> Z averageOf(DiscreteFunction<Z, Z> function) {
        FunctionSupport<Z> support = toSupportOfComparable();
        return new FunctionSupportWithConversion<>(support.environment, Functions.identity()).averageOf(function);
    }

    public final <Z extends Comparable<? super Z>> Z rmsOf(DiscreteFunction<Z, Z> function) {
        FunctionSupport<Z> support = toSupportOfComparable();
        return new FunctionSupportWithConversion<>(support.environment, Functions.identity()).rmsOf(function);
    }

    @SuppressWarnings("unchecked")
    <Z extends Comparable<? super Z>> FunctionSupport<Z> toSupportOfComparable() {
        try {
            return (FunctionSupport<Z>) this;
        } catch (ClassCastException exception) {
            throw new IllegalStateException("The type of the FunctionSupport that you use MUST implement Comparable");
        }
    }

}
