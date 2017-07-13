/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.tensorics.core.lang.TensoricDoubles;
import org.tensorics.core.lang.Tensorics;

import com.google.common.collect.ImmutableSet;

public class CoordinateClassHierarchyTest {
    public interface FirstCoordinateInterface {
        /* marker interface */
    }

    public enum FirstCoordinate implements FirstCoordinateInterface {
        FIRST_1,
        FIRST_2,
        FIRST_3;
    }

    public enum IncompatibleFirstCoordinate implements FirstCoordinateInterface {
        INCOMPATIBLEFIRST_1,
        INCOMPATIBLEFIRST_2;
    }

    public enum SecondCoordinate {
        SECOND_1,
        SECOND_2;
    }

    private static Tensor<Double> buildTensorFor(Class<? extends Enum<?>> first, Class<? extends Enum<?>> second,
            Class<?>... dimensions) {
        TensorBuilder<Double> builder = Tensorics.builder(dimensions);
        for (Object firstCoordinate : first.getEnumConstants()) {
            for (Object secondCoordinate : second.getEnumConstants()) {
                Object[] coordinates = { firstCoordinate, secondCoordinate };
                builder.put(Position.of(coordinates), 1.0);
            }
        }
        return builder.build();
    }

    private final Tensor<Double> leafClassTensor = buildTensorFor(FirstCoordinate.class, SecondCoordinate.class,
            FirstCoordinate.class, SecondCoordinate.class);
    private final Tensor<Double> compatibleInterfaceTensor = buildTensorFor(FirstCoordinate.class,
            SecondCoordinate.class, FirstCoordinateInterface.class, SecondCoordinate.class);
    private final Tensor<Double> incompatibleLeafTensor = buildTensorFor(IncompatibleFirstCoordinate.class,
            SecondCoordinate.class, IncompatibleFirstCoordinate.class, SecondCoordinate.class);
    private final Tensor<Double> incompatibleInterfaceTensor = buildTensorFor(IncompatibleFirstCoordinate.class,
            SecondCoordinate.class, FirstCoordinateInterface.class, SecondCoordinate.class);

    @Test
    public void shapeIsConservedInCalculation() {
        Tensor<Double> leafResult = TensoricDoubles.calculate(leafClassTensor).plus(leafClassTensor);
        assertEquals(leafClassTensor.shape(), leafResult.shape());
        Tensor<Double> interfaceResult = TensoricDoubles.calculate(compatibleInterfaceTensor)
                .plus(compatibleInterfaceTensor);
        assertEquals(compatibleInterfaceTensor.shape(), interfaceResult.shape());
    }

    @Test
    public void canBroadcastForTensorsWithIncompatibleDimensions() {
        Tensor<Double> result = TensoricDoubles.calculate(leafClassTensor).plus(incompatibleLeafTensor);
        assertEquals(ImmutableSet.of(FirstCoordinate.class, SecondCoordinate.class, IncompatibleFirstCoordinate.class),
                result.shape().dimensionSet());
    }

    @Test
    public void canAddTensorsWithCompatibleDimensionsAndCompatibleLeafCoordinates() {
        Tensor<Double> result = TensoricDoubles.calculate(leafClassTensor).plus(compatibleInterfaceTensor);
        assertEquals(compatibleInterfaceTensor.shape(), result.shape());
    }

    @Test
    public void canAddTensorsWithCompatibleDimensionsAndIncompatibleLeafCoordinates() {
        Tensor<Double> result = TensoricDoubles.calculate(leafClassTensor).plus(incompatibleInterfaceTensor);
        assertEquals(incompatibleInterfaceTensor.shape().dimensionSet(), result.shape().dimensionSet());
        result = TensoricDoubles.calculate(incompatibleInterfaceTensor).plus(leafClassTensor);
        assertEquals(incompatibleInterfaceTensor.shape().dimensionSet(), result.shape().dimensionSet());
    }

    @Test
    public void canAddTensorsWithEqualDimensionsAndIncompatibleLeafCoordinates() {
        Tensor<Double> result = TensoricDoubles.calculate(compatibleInterfaceTensor).plus(incompatibleInterfaceTensor);
        assertEquals(compatibleInterfaceTensor.shape().dimensionSet(), result.shape().dimensionSet());
        assertEquals(0, result.shape().size());
    }

    @Test
    public void canCompleteTensorsWithEqualDimensionsAndIncompatibleLeafCoordinates() {
        Tensorics.complete(compatibleInterfaceTensor).with(incompatibleInterfaceTensor);
    }

    @Test
    public void canCompleteTensorsWithCompatibleDimensionsAndIncompatibleLeafCoordinates() {
        Tensorics.complete(compatibleInterfaceTensor).with(incompatibleLeafTensor);
    }

    @Test
    public void canAddTensorsWithEqualDimensionsAndPartiallyIncompatibleLeafCoordinates() {
        Tensor<Double> mixedInterfaceTensor = Tensorics.complete(compatibleInterfaceTensor)
                .with(incompatibleInterfaceTensor);
        Tensor<Double> result = TensoricDoubles.calculate(compatibleInterfaceTensor).plus(mixedInterfaceTensor);
        assertEquals(compatibleInterfaceTensor.shape().dimensionSet(), result.shape().dimensionSet());
        assertEquals(6, result.shape().size());
    }

}
