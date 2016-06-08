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

package org.tensorics.core.function;

import java.util.Set;

/**
 * A function which has only a discrete set of points (X/Y pairs). The {@link #apply(Object)} method will throw an
 * {@link IllegalDiscreteFunctionUsageException} if a Y value is requested for an unknown X.
 * 
 * @author kfuchsbe
 * @param <X> the type of the values in x-direction (Independent variable)
 * @param <Y> the type of the values in y-direction (Dependent variable)
 */
public interface DiscreteFunction<X, Y> extends MathFunction<X, Y> {

    /**
     * @throws IllegalArgumentException in case the given x is not contained in the function
     */
    @Override
    Y apply(X input);

    /**
     * @return values for which the function is defined
     */
    Set<X> definedXValues();
}
