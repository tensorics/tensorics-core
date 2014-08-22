/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
        instantiatorFor(TestClassWithIntThrowingConstructor.class).withArgumentType(Integer.class).create(
                TEST_VALUE_5);
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
