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

package org.tensorics.core.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;
import static org.tensorics.core.util.InstantiatorType.CONSTRUCTOR;
import static org.tensorics.core.util.Instantiators.instantiatorFor;

import org.junit.Test;

public class InstantiatorsTest {

	private static final int TEST_VALUE_5 = 5;

	@Test
	public void verifyUtilityClass() {
		assertUtilityClass(Instantiators.class);
	}

	@Test(expected = NullPointerException.class)
	public void instantiatorWithNullClassThrows() {
		instantiatorFor(null);
	}

	@Test(expected = NullPointerException.class)
	public void instantiatorWithNullArgumentClassThrows() {
		instantiatorFor(TestClassWithIntConstructor.class).withArgumentType(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiatorWithNotMatchingConstructorThrows() {
		instantiatorFor(TestClassWithIntConstructor.class).withArgumentType(String.class);
	}

	@Test
	public void instantiatorWithCorrectArgumentsExists() {
		assertNotNull(workingInstantiator());
	}

	@Test
	public void instantiatorWithCorrectArgumentsWorks() {
		assertThat(workingInstantiator().create(5).integer, equalTo(TEST_VALUE_5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiatorForPackagePrivateConstructorThrows() throws Exception {
		instantiatorFor(TestClassWithPackageIntConstructor.class).withArgumentType(Integer.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiatorWithThrowingConstructorThrows() {
		instantiatorFor(TestClassWithIntThrowingConstructor.class).withArgumentType(Integer.class).create(TEST_VALUE_5);
	}

	private Instantiator<Integer, TestClassWithIntConstructor> workingInstantiator() {
		return instantiatorFor(TestClassWithIntConstructor.class).ofType(CONSTRUCTOR).withArgumentType(Integer.class);
	}

	private static class TestClassWithIntConstructor {
		final Integer integer;

		@SuppressWarnings("unused")
		/* will be called implicitely by the instantiator */
		public TestClassWithIntConstructor(Integer integer) {
			this.integer = integer;
		}
	}

	private static class TestClassWithPackageIntConstructor {

		@SuppressWarnings("unused")
		/* will be called implicitely by the instantiator */
		TestClassWithPackageIntConstructor(Integer integer) {
			/* Only for testing */
		}
	}

	private static class TestClassWithIntThrowingConstructor {

		@SuppressWarnings("unused")
		/* will be called implicitely by the instantiator */
		public TestClassWithIntThrowingConstructor(Integer integer) {
			throw new IllegalStateException("Thrown for testing");
		}
	}

}
