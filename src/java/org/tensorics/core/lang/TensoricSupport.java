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
import org.tensorics.core.quantity.options.ImmutableConfidenceLevel;
import org.tensorics.core.scalar.lang.OngoingScalarBinaryPredicate;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingQuantifiedTensorOperation;
import org.tensorics.core.tensor.lang.OngoingQuantityTensorValueExtraction;
import org.tensorics.core.tensor.lang.OngoingTensorOperation;
import org.tensorics.core.tensor.lang.QuantityTensorSupport;
import org.tensorics.core.tensor.lang.TensorSupport;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.lang.OngoingQuantifiedTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.OngoingTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.QuantityTensorbackedSupport;
import org.tensorics.core.tensorbacked.lang.TensorbackedSupport;
import org.tensorics.core.units.Unit;

/**
 * The main start point for expressions of the tensoric eDSL. Gives access to the quantified versions of the operations
 * and the basic tensor operations. <br>
 * <b>NOTE!</b><br>
 * Contains delegation methods only!
 *
 * @author kfuchsbe, agorzaws
 * @param <V> The type of the values of the scalars (elements of the field on which all the operations are based on)
 */
public class TensoricSupport<V> {

    private final TensorSupport<V> tensoricFieldUsage;
    private final QuantityTensorSupport<V> quantifiedTensoricFieldUsage;
    private final QuantityTensorbackedSupport<V> quantifiedTensorbackedSupport;
    private final TensorbackedSupport<V> tensorbackedSupport;
    private final EnvironmentImpl<V> environment;

    public TensoricSupport(EnvironmentImpl<V> environment) {
        this.environment = environment;
        this.tensoricFieldUsage = new TensorSupport<>(environment);
        this.quantifiedTensoricFieldUsage = new QuantityTensorSupport<>(environment);
        this.tensorbackedSupport = new TensorbackedSupport<>(environment);
        this.quantifiedTensorbackedSupport = new QuantityTensorbackedSupport<>(environment);
    }

    public OngoingBinaryOperation<V> calculate(V operand) {
        return tensoricFieldUsage.calculate(operand);
    }

    public final V avarageOf(Iterable<V> values) {
        return tensoricFieldUsage.averageOf(values);
    }

    public final V negativeOf(V element) {
        return tensoricFieldUsage.negativeOf(element);
    }

    public final V inverseOf(V element) {
        return tensoricFieldUsage.inverseOf(element);
    }

    public final V sizeOf(Iterable<V> values) {
        return tensoricFieldUsage.sizeOf(values);
    }

    public final V zero() {
        return tensoricFieldUsage.zero();
    }

    public final V two() {
        return tensoricFieldUsage.two();
    }

    public final V one() {
        return tensoricFieldUsage.one();
    }

    public V countOf(int number) {
        return tensoricFieldUsage.countOf(number);
    }

    public final V sumOf(Iterable<V> values) {
        return tensoricFieldUsage.sumOf(values);
    }

    public V rmsOf(Iterable<V> values) {
        return tensoricFieldUsage.rmsOf(values);
    }

    public V squareRootOf(V value) {
        return tensoricFieldUsage.squareRootOf(value);
    }

    public V squareOf(V value) {
        return tensoricFieldUsage.squareOf(value);
    }

    public final <C> OngoingTensorOperation<C, V> calculate(Tensor<V> tensoric) {
        return tensoricFieldUsage.calculate(tensoric);
    }

    public V sumOfSquaresOf(Iterable<V> values) {
        return tensoricFieldUsage.sumOfSquaresOf(values);
    }

    public <C> Tensor<V> zeros(Shape shape) {
        return tensoricFieldUsage.zeros(shape);
    }

    public <C> Tensor<V> ones(Shape shape) {
        return tensoricFieldUsage.ones(shape);
    }

    public <C> Tensor<V> elementInverseOf(Tensor<V> tensor) {
        return tensoricFieldUsage.elementInverseOf(tensor);
    }

    public <C> Tensor<V> negativeOf(Tensor<V> tensor) {
        return tensoricFieldUsage.negativeOf(tensor);
    }

    public final QuantifiedValue<V> averageOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.averageOf(values);
    }

    public final QuantifiedValue<V> rmsOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.rmsOf(values);
    }

    public final QuantifiedValue<V> varOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.varOf(values);
    }

    public final QuantifiedValue<V> stdOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.stdOf(values);
    }

    public OngoingQuantifiedTensorOperation<V> calculateQ(Tensor<QuantifiedValue<V>> left) {
        return quantifiedTensoricFieldUsage.calculate(left);
    }

    public QuantifiedValue<V> valueOf(V value, Unit unit) {
        return quantifiedTensoricFieldUsage.valueOf(value, unit);
    }

    public OngoingQuantifiedScalarOperation<V> calculate(V value, javax.measure.unit.Unit<?> unit) {
        return quantifiedTensoricFieldUsage.calculate(value, unit);
    }

    public OngoingQuantifiedScalarOperation<V> calculate(QuantifiedValue<V> scalar) {
        return quantifiedTensoricFieldUsage.calculate(scalar);
    }

    public QuantifiedValue<V> negativeOf(QuantifiedValue<V> element) {
        return quantifiedTensoricFieldUsage.negativeOf(element);
    }

    public QuantifiedValue<V> inverseOf(QuantifiedValue<V> element) {
        return quantifiedTensoricFieldUsage.inverseOf(element);
    }

    public QuantifiedValue<V> valueOf(V value, javax.measure.unit.Unit<?> unit) {
        return quantifiedTensoricFieldUsage.valueOf(value, unit);
    }

    public <TB extends Tensorbacked<V>> TB negativeOf(TB tensorBacked) {
        return tensorbackedSupport.negativeOf(tensorBacked);
    }

    public final <TB extends Tensorbacked<V>> OngoingTensorBackedOperation<TB, V> calculate(TB tensorBacked) {
        return tensorbackedSupport.calculate(tensorBacked);
    }

    public <TB extends Tensorbacked<QuantifiedValue<V>>> TB negativeOfQ(TB tensorBacked) {
        return quantifiedTensorbackedSupport.negativeOf(tensorBacked);
    }

    public <QTB extends Tensorbacked<QuantifiedValue<V>>> OngoingQuantifiedTensorBackedOperation<QTB, V> calculateQ(
            QTB left) {
        return quantifiedTensorbackedSupport.calculate(left);
    }

    public OngoingScalarBinaryPredicate<V> testIf(V left) {
        return tensoricFieldUsage.testIf(left);
    }

    public OngoingQuantifiedScalarBinaryPredicate<V> testIf(QuantifiedValue<V> left) {
        return quantifiedTensoricFieldUsage.testIf(left);
    }

    public V absoluteValueOf(V value) {
        return tensoricFieldUsage.absoluteValueOf(value);
    }

    public QuantifiedValue<V> absoluteValueOf(QuantifiedValue<V> value) {
        return quantifiedTensoricFieldUsage.absoluteValueOf(value);
    }

    public <S, R> Tensor<R> elementwise(BinaryFunction<S, R> operation, Tensor<S> left, Tensor<S> right) {
        return tensoricFieldUsage.elementwise(operation, left, right);
    }

    public Tensor<V> elementwise(BinaryOperation<V> operation, Tensor<V> left, Tensor<V> right) {
        return tensoricFieldUsage.elementwise(operation, left, right);
    }

    public OngoingQuantityValueExtraction<V> valueOf(QuantifiedValue<V> quantity) {
        return quantifiedTensoricFieldUsage.valueOf(quantity);
    }

    public final OngoingQuantityIterableValueExtraction<V> valuesOfI(Iterable<QuantifiedValue<V>> quantities) {
        return quantifiedTensoricFieldUsage.valuesOf(quantities);
    }

    public final OngoingQuantityTensorValueExtraction<V> valuesOf(Tensor<QuantifiedValue<V>> tensor) {
        return quantifiedTensoricFieldUsage.valuesOf(tensor);
    }

    public final QuantifiedValue<V> sizeOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.sizeOf(values);
    }

    public final QuantifiedValue<V> sumOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.sumOf(values);
    }

    public final OngoingQuantityIterableValueExtraction<V> valuesOf(Iterable<QuantifiedValue<V>> quantities) {
        return quantifiedTensoricFieldUsage.valuesOf(quantities);
    }

    public OngoingQuantifiedScalarConversion<V> convert(QuantifiedValue<V> value) {
        return quantifiedTensoricFieldUsage.convert(value);
    }

    public TensoricSupport<V> with(ManipulationOption newOption) {
        return new TensoricSupport<>(environment.with(newOption));
    }

    public ConfidenceLevel<V> confidenceLevelOf(V confidenceLevel) {
        return new ImmutableConfidenceLevel<>(confidenceLevel);
    }

}
