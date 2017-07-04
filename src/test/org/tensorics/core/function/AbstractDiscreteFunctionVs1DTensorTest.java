/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.TensoricDoubleSupport;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;

/**
 * The aim of this test is to compare the difference of features we have between {@link Tensor}s and
 * {@link DiscreteFunction}s.
 * <p>
 * This test focuses on testing binary operations (addition, substraction, multiplication). It also tests reduction
 * operations like AVG, RMS and STD.
 * 
 * @author caguiler
 */
public abstract class AbstractDiscreteFunctionVs1DTensorTest {

    private static final TensoricDoubleSupport SUPPORT = new TensoricDoubleSupport();
    protected Tensor<Double> two;
    protected Tensor<Double> three;

    @Test
    public void testTensorAddition() {
        Tensor<Double> five = SUPPORT.calculate(two).plus(three);
        assertTensorNotEmpty(five);
        assertThatAllTensorElementsEqualTo(five, 5.);
        assertThatTensorContainsCoordinatesOfTwoOperands(five, two, three);
    }

    @Test
    public void testTensorSubstraction() {
        Tensor<Double> minusOne = SUPPORT.calculate(two).minus(three);
        assertTensorNotEmpty(minusOne);
        assertThatAllTensorElementsEqualTo(minusOne, -1.);
        assertThatTensorContainsCoordinatesOfTwoOperands(minusOne, two, three);

        Tensor<Double> one = SUPPORT.calculate(three).minus(two);
        assertTensorNotEmpty(one);
        assertThatAllTensorElementsEqualTo(one, 1.0);
        assertThatTensorContainsCoordinatesOfTwoOperands(one, two, three);
    }

    @Test
    public void testTensorTimes() {
        Tensor<Double> six = SUPPORT.calculate(two).elementTimes(three);
        assertTensorNotEmpty(six);
        assertThatAllTensorElementsEqualTo(six, 6.);
        assertThatTensorContainsCoordinatesOfTwoOperands(six, two, three);
    }

    @Test
    public void testTensorDivision() {
        Tensor<Double> thwoThirds = SUPPORT.calculate(two).elementDividedBy(three);
        Tensor<Double> threeHalfs = SUPPORT.calculate(three).elementDividedBy(two);
        assertTensorNotEmpty(thwoThirds);
        assertTensorNotEmpty(threeHalfs);
        assertThatAllTensorElementsEqualTo(thwoThirds, 2 / 3.);
        assertThatAllTensorElementsEqualTo(threeHalfs, 3 / 2.);

        assertThatTensorContainsCoordinatesOfTwoOperands(threeHalfs, two, three);
        assertThatTensorContainsCoordinatesOfTwoOperands(thwoThirds, two, three);
    }

    @Test
    public void testTensorAverage() {
        Tensor<Double> average = Tensorics.from(two).reduce(Double.class).byAveragingIn(Structures.doubles());
        assertTensorNotEmpty(average);
        assertEquals(2.0, average.get(), Double.MIN_VALUE);
        assertThatAllTensorElementsEqualTo(average, 2.);
    }

    @Test
    public void testTensorRms() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testTensorStd() {
        throw new UnsupportedOperationException();
    }

    private <T> void assertThatAllTensorElementsEqualTo(Tensor<T> tensor, T value) {
        assertTrue(Tensorics.mapFrom(tensor).values().stream().allMatch(value::equals));
    }

    private <T> void assertTensorNotEmpty(Tensor<T> tensor) {
        assertFalse(Tensorics.mapFrom(tensor).isEmpty());
    }

    private <T> void assertThatTensorContainsCoordinatesOfTwoOperands(Tensor<T> tensorResult, Tensor<T> operand1,
            Tensor<T> operan2) {
        Set<Double> operand1Coords = operand1.shape().coordinatesOfType(Double.class);
        Set<Double> operand2Coords = operan2.shape().coordinatesOfType(Double.class);

        Set<Double> tensorResultCoords = tensorResult.shape().coordinatesOfType(Double.class);

        assertTrue(tensorResultCoords.containsAll(operand1Coords));
        assertTrue(tensorResultCoords.containsAll(operand2Coords));
    }
}
