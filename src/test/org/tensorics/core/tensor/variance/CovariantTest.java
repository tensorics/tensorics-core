/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.variance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.tensorics.core.util.Instantiator;

public class CovariantTest {

    private static final String TEST_VALUE_A = "A";

    @Test(expected = NullPointerException.class)
    public void nullConstructorArgumentThrows() {
        stringInstantiator().create(null);
    }

    @Test
    public void getReturnsCorrectValue() {
        assertThat(covariantTestValue().get(), equalTo(TEST_VALUE_A));
    }

    @Test
    public void twoCreatedInstancesEqual() {
        assertThat(covariantTestValue(), equalTo(covariantTestValue()));
    }

    private CovariantString covariantTestValue() {
        return stringInstantiator().create(TEST_VALUE_A);
    }

    private Instantiator<String, CovariantString> stringInstantiator() {
        return Covariants.instantiatorFor(CovariantString.class);
    }

    public static class CovariantString extends Covariant<String> {

        private CovariantString(String argument) {
            super(argument);
        }

        public static CovariantString of(String argument) {
            return new CovariantString(argument);
        }

    }

}
