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

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.TensoricSupport;

/**
 * Provides additional methods and fluent clauses for needs in testing
 * tensorics. All the methods which require e.g. the knowledge of field or
 * similar aspects have to go in here. All the others (which are more
 * structural) will be places in
 * {@link org.tensorics.core.testing.TensoricTests}.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values of the field which is used
 * @see org.tensorics.core.testing.TensoricTests
 */
public class TensoricTestingSupport<S> extends TensoricSupport<S> {

	private final Environment<S> environment;

	public TensoricTestingSupport(EnvironmentImpl<S> environment) {
		super(environment);
		this.environment = environment;
	}

	public OngoingCloseToMatcherCreation<S> within(S tolerance) {
		return new OngoingCloseToMatcherCreation<>(environment, tolerance);
	}

}
