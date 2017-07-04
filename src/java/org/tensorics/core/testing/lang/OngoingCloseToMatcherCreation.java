// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

package org.tensorics.core.testing.lang;

import org.hamcrest.Matcher;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.scalar.lang.ScalarSupport;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorSupport;
import org.tensorics.core.testing.hamcrest.ScalarIsCloseTo;
import org.tensorics.core.testing.hamcrest.TensorIsCloseTo;

/**
 * Part of a fluent clause to create a matcher which will allow to check if some
 * value is 'close to' some other value.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar for which a matcher has to be created
 */
public class OngoingCloseToMatcherCreation<S> {

	private final Environment<S> environment;
	private final S tolerance;

	public OngoingCloseToMatcherCreation(Environment<S> environment, S tolerance) {
		super();
		this.environment = environment;
		this.tolerance = tolerance;
	}

	public Matcher<S> closeTo(S value) {
		return new ScalarIsCloseTo<S>(new ScalarSupport<>(environment.field()), value, tolerance);
	}

	public Matcher<Tensor<S>> closeTo(Tensor<S> tensor) {
		return new TensorIsCloseTo<S>(new TensorSupport<>(environment), tensor, tolerance);
	}
}
