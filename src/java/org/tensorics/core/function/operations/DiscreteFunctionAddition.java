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

/**
 * The {@link AbstractDiscreteFunctionBinaryOperation} that describes the addition of two {@link DiscreteFunction}s
 *
 * @author caguiler
 * @param <X> the type of the independent variable in the {@link DiscreteFunction}.
 * @param <Y> the type of the dependent variable in the {@link DiscreteFunction}
 */
public class DiscreteFunctionAddition<X, Y> extends AbstractDiscreteFunctionBinaryOperation<X, Y> {
    private static final long serialVersionUID = 1L;

    DiscreteFunctionAddition(Environment<Y> environment, Conversion<X, Y> conversion, Comparator<X> comparator) {
        super(environment, conversion, environment.field().addition(), comparator);
    }
}
