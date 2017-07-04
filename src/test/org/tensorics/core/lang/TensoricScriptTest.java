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

package org.tensorics.core.lang;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ExpressionIsUnresolvedException;

public class TensoricScriptTest {

	private TensoricScript<Double, Double> script;

	@Before
	public void setUpBeforeClass() throws Exception {
		script = new DoubleScript<Double>() {

			@Override
			protected Expression<Double> describe() {
				return null;
			}
		};
	}

	@Test
	public void scriptIsNotResolved() {
		assertFalse(script.isResolved());
	}

	@Test(expected = ExpressionIsUnresolvedException.class)
	public void scriptThrowsOnGet() {
		script.get();
	}
}
