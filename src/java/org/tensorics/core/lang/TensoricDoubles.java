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
package org.tensorics.core.lang;

import org.tensorics.core.commons.lang.OngoingBinaryOperation;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.iterable.lang.OngoingQuantityIterableValueExtraction;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.OngoingQuantifiedScalarBinaryPredicate;
import org.tensorics.core.quantity.lang.OngoingQuantifiedScalarConversion;
import org.tensorics.core.quantity.lang.OngoingQuantifiedScalarOperation;
import org.tensorics.core.quantity.lang.OngoingQuantityValueExtraction;
import org.tensorics.core.quantity.options.ConfidenceLevel;
import org.tensorics.core.scalar.lang.OngoingScalarBinaryPredicate;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingQuantifiedTensorOperation;
import org.tensorics.core.tensor.lang.OngoingQuantityTensorValueExtraction;
import org.tensorics.core.tensor.lang.OngoingTensorOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.lang.OngoingQuantifiedTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.OngoingTensorBackedOperation;
import org.tensorics.core.units.Unit;

/**
 * Provides delegate methods to a static instance of a {@code TensoricSupport<Double>}. This is for convenience
 * purposes, so that a simple calculation does not have to inherit from the support class, but can statically import
 * methods from this class.
 *
 * @author kfuchsbe
 */
public final class TensoricDoubles {

    private static final TensoricDoubleSupport SUPPORT = new TensoricDoubleSupport();
    // private static final TensoricsDoubleExpressionSupport EXPRESSION_SUPPORT = new
    // TensoricsDoubleExpressionSupport();

    private TensoricDoubles() {
        /* only static methods */
    }

    public static final OngoingBinaryOperation<Double> calculate(Double operand) {
        return SUPPORT.calculate(operand);
    }

    public static final Double avarageOf(Iterable<Double> values) {
        return SUPPORT.avarageOf(values);
    }

    public static final Double negativeOf(Double element) {
        return SUPPORT.negativeOf(element);
    }

    public static final Double inverseOf(Double element) {
        return SUPPORT.inverseOf(element);
    }

    public static final Double sizeOf(Iterable<Double> values) {
        return SUPPORT.sizeOf(values);
    }

    public static final Double zero() {
        return SUPPORT.zero();
    }

    public static final Double two() {
        return SUPPORT.two();
    }

    public static final Double one() {
        return SUPPORT.one();
    }

    public static final Double countOf(int number) {
        return SUPPORT.countOf(number);
    }

    public static final Double sumOf(Iterable<Double> values) {
        return SUPPORT.sumOf(values);
    }

    public static final Double rmsOf(Iterable<Double> values) {
        return SUPPORT.rmsOf(values);
    }

    public static final Double squareRootOf(Double value) {
        return SUPPORT.squareRootOf(value);
    }

    public static final Double squareOf(Double value) {
        return SUPPORT.squareOf(value);
    }

    public static final <C> OngoingTensorOperation<C, Double> calculate(Tensor<Double> tensoric) {
        return SUPPORT.calculate(tensoric);
    }

    public static final Double sumOfSquaresOf(Iterable<Double> values) {
        return SUPPORT.sumOfSquaresOf(values);
    }

    public static final <C> Tensor<Double> zeros(Shape shape) {
        return SUPPORT.zeros(shape);
    }

    public static final <C> Tensor<Double> ones(Shape shape) {
        return SUPPORT.ones(shape);
    }

    public static final <C> Tensor<Double> elementInverseOf(Tensor<Double> tensor) {
        return SUPPORT.elementInverseOf(tensor);
    }

    public static final <C> Tensor<Double> negativeOf(Tensor<Double> tensor) {
        return SUPPORT.negativeOf(tensor);
    }

    public static final QuantifiedValue<Double> averageOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.averageOfQ(values);
    }

    public static final QuantifiedValue<Double> rmsOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.rmsOfQ(values);
    }

    public static final QuantifiedValue<Double> varOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.varOfQ(values);
    }

    public static final QuantifiedValue<Double> stdOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.stdOfQ(values);
    }

    public static final OngoingQuantifiedTensorOperation<Double> calculateQ(Tensor<QuantifiedValue<Double>> left) {
        return SUPPORT.calculateQ(left);
    }

    public static final QuantifiedValue<Double> sizeOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.sizeOfQ(values);
    }

    public static final QuantifiedValue<Double> valueOf(Double value, Unit unit) {
        return SUPPORT.valueOf(value, unit);
    }

    public static final OngoingQuantifiedScalarOperation<Double> calculate(Double value,
            javax.measure.unit.Unit<?> unit) {
        return SUPPORT.calculate(value, unit);
    }

    public static final QuantifiedValue<Double> sumOfQ(Iterable<QuantifiedValue<Double>> values) {
        return SUPPORT.sumOfQ(values);
    }

    public static final OngoingQuantifiedScalarOperation<Double> calculate(QuantifiedValue<Double> scalar) {
        return SUPPORT.calculate(scalar);
    }

    public static final QuantifiedValue<Double> negativeOf(QuantifiedValue<Double> element) {
        return SUPPORT.negativeOf(element);
    }

    public static final QuantifiedValue<Double> inverseOf(QuantifiedValue<Double> element) {
        return SUPPORT.inverseOf(element);
    }

    public static final <TB extends Tensorbacked<Double>> TB negativeOf(TB tensorBacked) {
        return SUPPORT.negativeOf(tensorBacked);
    }

    public static final <TB extends Tensorbacked<Double>> OngoingTensorBackedOperation<TB, Double> calculate(
            TB tensorBacked) {
        return SUPPORT.calculate(tensorBacked);
    }

    public static final <TB extends Tensorbacked<QuantifiedValue<Double>>> TB negativeOfQ(TB tensorBacked) {
        return SUPPORT.negativeOfQ(tensorBacked);
    }

    public static final <QTB extends Tensorbacked<QuantifiedValue<Double>>> OngoingQuantifiedTensorBackedOperation<QTB, Double> calculateQ(
            QTB left) {
        return SUPPORT.calculateQ(left);
    }

    public static final OngoingScalarBinaryPredicate<Double> testIf(Double left) {
        return SUPPORT.testIf(left);
    }

    public static final OngoingQuantifiedScalarBinaryPredicate<Double> testIf(QuantifiedValue<Double> left) {
        return SUPPORT.testIf(left);
    }

    public static final Double absoluteValueOf(Double value) {
        return SUPPORT.absoluteValueOf(value);
    }

    public static final QuantifiedValue<Double> absoluteValueOf(QuantifiedValue<Double> value) {
        return SUPPORT.absoluteValueOf(value);
    }

    public static final <S, R> Tensor<R> elementwise(BinaryFunction<S, R> operation, Tensor<S> left, Tensor<S> right) {
        return SUPPORT.elementwise(operation, left, right);
    }

    public static final Tensor<Double> elementwise(BinaryOperation<Double> operation, Tensor<Double> left,
            Tensor<Double> right) {
        return SUPPORT.elementwise(operation, left, right);
    }

    public static final QuantifiedValue<Double> valueOf(Double value, javax.measure.unit.Unit<?> unit) {
        return SUPPORT.valueOf(value, unit);
    }

    public static final OngoingQuantityValueExtraction<Double> valueOf(QuantifiedValue<Double> quantity) {
        return SUPPORT.valueOf(quantity);
    }

    public static final OngoingQuantityIterableValueExtraction<Double> valuesOfI(
            Iterable<QuantifiedValue<Double>> quantities) {
        return SUPPORT.valuesOfI(quantities);
    }

    public static final ConfidenceLevel<Double> confidenceLevelOf(Double confidenceLevel) {
        return SUPPORT.confidenceLevelOf(confidenceLevel);
    }

    public static final TensoricSupport<Double> with(ManipulationOption newOption) {
        return SUPPORT.with(newOption);
    }

    public static final OngoingQuantityIterableValueExtraction<Double> valuesOf(
            Iterable<QuantifiedValue<Double>> quantities) {
        return SUPPORT.valuesOf(quantities);
    }

    public static final OngoingQuantifiedScalarConversion<Double> convert(QuantifiedValue<Double> value) {
        return SUPPORT.convert(value);
    }

    public static final OngoingQuantityTensorValueExtraction<Double> valuesOf(Tensor<QuantifiedValue<Double>> tensor) {
        return SUPPORT.valuesOf(tensor);
    }

}
