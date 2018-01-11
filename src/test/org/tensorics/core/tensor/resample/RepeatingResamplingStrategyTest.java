package org.tensorics.core.tensor.resample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RepeatingResamplingStrategyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullInDimensionListThrows() {
        thrown.expect(NullPointerException.class);
        RepeatingResamplingStrategy.of(null);
    }

}
