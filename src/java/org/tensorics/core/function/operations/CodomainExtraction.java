// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2016, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on
package org.tensorics.core.function.operations;

import java.util.Collection;
import java.util.stream.Collectors;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.DiscreteFunction;

import com.google.common.base.Preconditions;

/**
 * A conversion that takes a {@link DiscreteFunction} and produces an iterable that contains the values of its codomain. See <a href="https://en.wikipedia.org/wiki/Codomain">Codomain</a>
 *
 * @param <Y> the type of the dependent variable (output)of the discrete function
 * @author caguiler
 */
public class CodomainExtraction<Y> implements Conversion<DiscreteFunction<?, Y>, Iterable<Y>> {

    @Override
    public Iterable<Y> apply(DiscreteFunction<?, Y> inputfunction) {
        return yValuesOf(inputfunction);
    }

    private static <X, Y> Collection<Y> yValuesOf(DiscreteFunction<X, Y> function) {
        Preconditions.checkNotNull(function, "function cannot be null!");
        return function.definedXValues().stream().map(function::apply).collect(Collectors.toList());
    }
}
