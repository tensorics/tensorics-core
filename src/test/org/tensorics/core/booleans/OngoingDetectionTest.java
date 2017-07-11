/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.junit.Assert.assertTrue;
import static org.tensorics.core.lang.Tensorics.at;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

public class OngoingDetectionTest extends AbstractBooleanTest {

    @Test
    public void testChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 1);
        Tensor<Boolean> result = calcLogical(tensorTrue).and(tensorTrue);
        System.out.println(result);
        Iterable<Integer> changes = detectWhere(tensorTrue).changesAlong(Integer.class);
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(CHANGE_OF_THE_SIGNAL));
    }

    @Test
    public void testMoreChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 10);
        Tensor<Boolean> result = calcLogical(tensorTrue).and(tensorTrue);

        System.out.println(result);
        Iterable<Integer> changes = detectWhere(tensorTrue).changesAlong(Integer.class);
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDimensionWithoutProviededComparator() {
        Tensor<Boolean> tensorTrue = createTensorOfOneNoncomparableDimension();
        detectWhere(tensorTrue).changesAlong(Integer.class);
    }

    @Test
    public void testDimensionWithComparator() {
        Tensor<Boolean> tensorTrue = createTensorOfOneNoncomparableDimension();
        detectWhere(tensorTrue).changesAlong(TestNonComparableClass.class, (o1, o2) -> (o1.getField() - o2.getField()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDirection() {
        Tensor<Boolean> tensor = createSimpleOneComparableDimensionTensorOf(true, 2, 1);
        @SuppressWarnings("unused")
        Iterable<Double> changes2 = detectWhere(tensor).changesAlong(Double.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLargeTensor() {
        Tensor<Boolean> tensorTrue = createTensorOf(true, 12, 4);
        @SuppressWarnings("unused")
        Iterable<Integer> changes2 = detectWhere(tensorTrue).changesAlong(Integer.class);
    }

    private static Tensor<Boolean> createTensorOfOneNoncomparableDimension() {
        TensorBuilder<Boolean> builder = Tensorics.builder(TestNonComparableClass.class);
        for (int i = 0; i < 10; i++) {
            boolean toPut = (i > 5 ? true : false);
            TestNonComparableClass testNonComparableClass = new TestNonComparableClass(i);
            builder.put(at(testNonComparableClass), toPut);
        }
        return builder.build();
    }
}

class TestNonComparableClass {

    private final int field;

    public TestNonComparableClass(int field) {
        this.field = field;
    }

    public int getField() {
        return field;
    }
}
