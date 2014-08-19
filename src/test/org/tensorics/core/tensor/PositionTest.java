package org.tensorics.core.tensor;

import static org.tensorics.core.tensor.Positions.assertConsistentDimensions;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class PositionTest {

    private static final Position POS_A = Position.of("A");

    @Test(expected = IllegalArgumentException.class)
    public void coordinatesContainPositionThrows() {
        Position.of(POS_A);
    }

    @Test
    public void assertConsistentWithCorrectSet() {
        assertConsistentDimensions(POS_A, ImmutableSet.of(String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertConsistentWithWrongSetThrows() {
        assertConsistentDimensions(POS_A, ImmutableSet.of(Integer.class));
    }

}
