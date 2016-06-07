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
public class FunctionSupport<Y> extends FunctionSupportWithConversion<Y, Y> {

    public FunctionSupport(Environment<Y> environment) {
        super(environment, Functions.identity());
    }

    public final <X> FunctionSupportWithConversion<X, Y> withConversion(Function<X, Y> conversion) {
        return new FunctionSupportWithConversion<>(environment(), conversion);
    }
}
