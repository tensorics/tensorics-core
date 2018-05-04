/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.fields.doubles.Structures.doubles;
import static org.tensorics.core.lang.Tensorics.at;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

public class InterpolationAtTest {

    private static final double DOUBLE_COMPARISON_TOLERANCE = 1e-6;
    private static final int SIMPLE_TENSOR_PROPER_SLICE_SIZE = 2;
    private static final int BIG_TENSOR_PROPER_SLICE_SIZE = 4;
    private static final int NUMBER_OF_INCOPLETE_IN_THE_MIDDLE = 1;
    private static final int NUMBER_OF_INCOPLETE_AT_THE_END = 2;

    private final ComparableCoordinate NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE = new ComparableCoordinate(2);
    private final ComparableCoordinate NOT_COMPLETE_COMPARABLE_COORDINATE_AT_THE_END = new ComparableCoordinate(5);
    private final ComparableCoordinate COORDINATE_BEFORE_THE_FIRST = new ComparableCoordinate(-1);
    private final ComparableCoordinate COORDINATE_AFTER_THE_LAST = new ComparableCoordinate(10);
    private Tensor<Double> testTenosor;

    @Test
    public void testToObtainSimpleSliceSimpleTensor() {

        testTenosor = getTensorTwoCoordinates();
        Tensor<Double> bySlicingAt = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .bySlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE);
        assertEquals(SIMPLE_TENSOR_PROPER_SLICE_SIZE - 1, bySlicingAt.shape().positionSet().size());
    }

    @Test
    public void testToObtainInterpolatedInTheMiddleSimpleTensor() {
        testTenosor = getTensorTwoCoordinates();

        Tensor<Double> byInterpolatedSlicingAt = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .byInterpolatedSlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE)
                .withLinearInterpolation(doubles(), ComparableCoordinate::getDouble);
        assertEquals(SIMPLE_TENSOR_PROPER_SLICE_SIZE, byInterpolatedSlicingAt.shape().positionSet().size());
    }

    @Test
    public void testToObtainSimpleSliceBigTensor() {

        testTenosor = getTensorThreeCoordinates();

        Tensor<Double> bySlicingAt = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .bySlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE);

        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE - NUMBER_OF_INCOPLETE_IN_THE_MIDDLE,
                bySlicingAt.shape().positionSet().size());

        bySlicingAt = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .bySlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_AT_THE_END);
        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE - NUMBER_OF_INCOPLETE_AT_THE_END,
                bySlicingAt.shape().positionSet().size());
    }

    @Test
    public void testToObtainInterpolatedInTheMiddle() {
        testTenosor = getTensorThreeCoordinates();

        Tensor<Double> interpolated = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .byInterpolatedSlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE)
                .withLinearInterpolation(doubles(), ComparableCoordinate::getDouble);

        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE, interpolated.shape().positionSet().size());

        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM1), 2.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM1), 2.5,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM2), 2.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM2), 42.0,
                DOUBLE_COMPARISON_TOLERANCE);

    }

    @Test
    public void extrapolateValuesBeforeTheFirstByInterpolation() {
        testTenosor = getTensorThreeCoordinates();

        Tensor<Double> interpolated = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .byInterpolatedSlicingAt(COORDINATE_BEFORE_THE_FIRST)
                .withLinearInterpolation(doubles(), ComparableCoordinate::getDouble);

        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE, interpolated.shape().positionSet().size());

        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM1), -1.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM1), -2.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM2), -1.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM2), -81.0,
                DOUBLE_COMPARISON_TOLERANCE);
    }

    @Test
    public void extrapolateValuesAfterTheLastByInterpolation() {
        testTenosor = getTensorThreeCoordinates();

        Tensor<Double> interpolated = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .byInterpolatedSlicingAt(COORDINATE_AFTER_THE_LAST)
                .withLinearInterpolation(doubles(), ComparableCoordinate::getDouble);

        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE, interpolated.shape().positionSet().size());

        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM1), 1.75,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM1), 23.9,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST1"), TestEnum.ENUM2), 14.0,
                DOUBLE_COMPARISON_TOLERANCE);
        assertEquals(interpolated.get(new TestNameCoordinate("TEST2"), TestEnum.ENUM2), 0.5,
                DOUBLE_COMPARISON_TOLERANCE);
    }

    @Test
    public void testToObtainInterpolatedAtTheEnd() {
        testTenosor = getTensorThreeCoordinates();

        Tensor<Double> byInterpolatedSlicingAt = Tensorics.from(testTenosor).reduce(ComparableCoordinate.class)
                .byInterpolatedSlicingAt(NOT_COMPLETE_COMPARABLE_COORDINATE_AT_THE_END)
                .withLinearInterpolation(doubles(), ComparableCoordinate::getDouble);

        assertEquals(BIG_TENSOR_PROPER_SLICE_SIZE, byInterpolatedSlicingAt.shape().positionSet().size());

    }

    private Tensor<Double> getTensorTwoCoordinates() {
        TensorBuilder<Double> builder = Tensorics.builder(ComparableCoordinate.class, TestNameCoordinate.class);
        Object[] coordinates = { new ComparableCoordinate(1), new TestNameCoordinate("TEST1") };

        builder.put(at(coordinates), 1.0);
        Object[] coordinates1 = { new ComparableCoordinate(1), new TestNameCoordinate("TEST2") };
        builder.put(at(coordinates1), 1.0);
        Object[] coordinates2 = { NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE, new TestNameCoordinate("TEST1") };
        builder.put(at(coordinates2), 2.0);
        Object[] coordinates3 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST1") };
        builder.put(at(coordinates3), 3.5);
        Object[] coordinates4 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST2") };
        builder.put(at(coordinates4), 4.0);

        return builder.build();
    }

    private Tensor<Double> getTensorThreeCoordinates() {
        TensorBuilder<Double> builder = Tensorics.builder(ComparableCoordinate.class, TestNameCoordinate.class,
                TestEnum.class);
        Object[] coordinates = { new ComparableCoordinate(1), new TestNameCoordinate("TEST1"), TestEnum.ENUM1 };

        // enum1
        builder.put(at(coordinates), 1.0);
        Object[] coordinates1 = { new ComparableCoordinate(1), new TestNameCoordinate("TEST2"), TestEnum.ENUM1 };
        builder.put(at(coordinates1), 1.0);
        Object[] coordinates2 = { NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE, new TestNameCoordinate("TEST1"),
                TestEnum.ENUM1 };
        builder.put(at(coordinates2), 2.0);
        Object[] coordinates3 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST1"), TestEnum.ENUM1 };
        builder.put(at(coordinates3), 3.5);
        Object[] coordinates4 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST2"), TestEnum.ENUM1 };
        builder.put(at(coordinates4), 4.0);
        Object[] coordinates5 = { new ComparableCoordinate(4), new TestNameCoordinate("TEST1"), TestEnum.ENUM1 };
        builder.put(at(coordinates5), 3.25);
        Object[] coordinates6 = { new ComparableCoordinate(4), new TestNameCoordinate("TEST2"), TestEnum.ENUM1 };
        builder.put(at(coordinates6), 0.32);
        Object[] coordinates7 = { NOT_COMPLETE_COMPARABLE_COORDINATE_AT_THE_END, new TestNameCoordinate("TEST2"),
                TestEnum.ENUM1 };
        builder.put(at(coordinates7), 4.25);
        Object[] coordinates8 = { new ComparableCoordinate(1), new TestNameCoordinate("TEST1"), TestEnum.ENUM2 };

        // enum2
        builder.put(at(coordinates8), 1.0);
        Object[] coordinates9 = { new ComparableCoordinate(1), new TestNameCoordinate("TEST2"), TestEnum.ENUM2 };
        builder.put(at(coordinates9), 1.0);
        Object[] coordinates10 = { NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE, new TestNameCoordinate("TEST1"),
                TestEnum.ENUM2 };
        builder.put(at(coordinates10), 2.0);
        Object[] coordinates11 = { NOT_COMPLETE_COMPARABLE_COORDINATE_IN_THE_MIDDLE, new TestNameCoordinate("TEST2"),
                TestEnum.ENUM2 };
        builder.put(at(coordinates11), 42.0);
        Object[] coordinates12 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST1"), TestEnum.ENUM2 };
        builder.put(at(coordinates12), 3.5);
        Object[] coordinates13 = { new ComparableCoordinate(3), new TestNameCoordinate("TEST2"), TestEnum.ENUM2 };
        builder.put(at(coordinates13), 4.0);
        Object[] coordinates14 = { NOT_COMPLETE_COMPARABLE_COORDINATE_AT_THE_END, new TestNameCoordinate("TEST2"),
                TestEnum.ENUM2 };
        builder.put(at(coordinates14), 3.0);
        return builder.build();
    }

    private class ComparableCoordinate implements Comparable<ComparableCoordinate> {

        private final int coordinate;

        public ComparableCoordinate(int coordinate) {
            this.coordinate = coordinate;
        }

        public double getDouble() {
            return coordinate;
        }

        @Override
        public int compareTo(ComparableCoordinate o) {
            return coordinate - o.coordinate;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + coordinate;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ComparableCoordinate other = (ComparableCoordinate) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (coordinate != other.coordinate) {
                return false;
            }
            return true;
        }

        private InterpolationAtTest getOuterType() {
            return InterpolationAtTest.this;
        }

        @Override
        public String toString() {
            return "ComparableCoordinate [coordinate=" + coordinate + "]";
        }

    }

    private enum TestEnum {
        ENUM1,
        ENUM2
    }

    private class TestNameCoordinate {
        private final String name;

        public TestNameCoordinate(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            TestNameCoordinate other = (TestNameCoordinate) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (getName() == null) {
                if (other.getName() != null) {
                    return false;
                }
            } else if (!getName().equals(other.getName())) {
                return false;
            }
            return true;
        }

        private InterpolationAtTest getOuterType() {
            return InterpolationAtTest.this;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "TestNameCoordinate [name=" + name + "]";
        }

    }

}
