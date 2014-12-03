package org.tensorics.core.tensor;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensor.Positions.union;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import org.junit.Test;

public class PositionsTest {

    private final static Position POS_A = Position.of("A");
    private final static Position POS_B = Position.of("B");
    private final static Position POS_1 = Position.of(1);
    private final static Position POS_A1 = Position.of("A", 1);
    private final static Position POS_1A = Position.of(1, "A");

    @Test
    public void verifyUtilityClass() {
        assertUtilityClass(Positions.class);
    }

    @Test
    public void pos1AEqualsPosA1() {
        assertEquals(POS_1A, POS_A1);
    }

    @Test(expected = NullPointerException.class)
    public void leftNullThrowsException() {
        union(null, Position.empty());
    }

    @Test(expected = NullPointerException.class)
    public void rightNullThrowsException() {
        union(Position.empty(), null);
    }

    @Test
    public void unionEmptyWithEmpyIsEmpty() {
        Position result = union(Position.empty(), Position.empty());
        assertEquals(Position.empty(), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void overlappingDimensionsThrowException() {
        union(POS_A, POS_B);
    }

    @Test
    public void unionWithNonOverlappingDimensionsWorksCorrectly() {
        assertEquals(POS_1A, union(POS_1, POS_A));
    }

}
