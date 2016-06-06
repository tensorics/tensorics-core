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

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.SingleTypedDiscreteFunction;
import org.tensorics.core.scalar.lang.ScalarSupport;

import com.google.common.base.Function;

/**
 * 
 * Provides starting methods for tensoric language expressions that operate on
 * {@link DiscreteFunction}s.
 * 
 * @author caguiler
 *
 * @param <Y>
 *            the type of the scalar values (elements of the fields) on which to
 *            operate
 */
public class FunctionSupport<Y> extends ScalarSupport<Y> {

	private final Environment<Y> environment;

	public FunctionSupport(Environment<Y> environment) {
		super(environment.field());
		this.environment = environment;
	}

	public <X extends Comparable<? super X>> FunctionSupportWithConversion<X, Y> withConversion(
			Function<X, Y> conversion) {
		return new FunctionSupportWithConversion<>(environment, conversion);
	}

	public <Z extends Comparable<? super Z>> OngoingSingleTypedDiscreteFunctionOperation<Z> calculate(
			SingleTypedDiscreteFunction<Z> left) {
		return new OngoingSingleTypedDiscreteFunctionOperation<>((Environment<Z>) environment, left);
	}
}
