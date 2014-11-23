/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.commons.lang.OngoingBinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.OngoingQuantifiedScalarOperation;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingQuantifiedTensorOperation;
import org.tensorics.core.tensor.lang.OngoingTensorManipulation;
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
@SuppressWarnings("PMD.TooManyMethods")
public class TensoricSupport<V> {

    private final TensorSupport<V> tensoricFieldUsage;
    private final QuantityTensorSupport<V> quantifiedTensoricFieldUsage;
    private final QuantityTensorbackedSupport<V> quantifiedTensorbackedSupport;
    private final TensorbackedSupport<V> tensorbackedSupport;

    public TensoricSupport(EnvironmentImpl<V> environment) {
        this.tensoricFieldUsage = new TensorSupport<>(environment);
        this.quantifiedTensoricFieldUsage = new QuantityTensorSupport<>(environment);
        this.tensorbackedSupport = new TensorbackedSupport<>(environment);
        this.quantifiedTensorbackedSupport = new QuantityTensorbackedSupport<>(environment);
    }

    public OngoingBinaryOperation<V> calculate(V operand) {
        return tensoricFieldUsage.calculate(operand);
    }

    public final V avarageOf(Iterable<V> values) {
        return tensoricFieldUsage.avarageOf(values);
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

    public <E1> OngoingTensorManipulation<E1> from(Tensor<E1> tensor) {
        return tensoricFieldUsage.from(tensor);
    }

    public final QuantifiedValue<V> avarageOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.avarageOf(values);
    }

    public OngoingQuantifiedTensorOperation<V> calculateQ(Tensor<QuantifiedValue<V>> left) {
        return quantifiedTensoricFieldUsage.calculate(left);
    }

    public final QuantifiedValue<V> sizeOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.sizeOf(values);
    }

    public QuantifiedValue<V> valueOf(V value, Unit unit) {
        return quantifiedTensoricFieldUsage.valueOf(value, unit);
    }

    public OngoingQuantifiedScalarOperation<V> calculate(V value, javax.measure.unit.Unit<?> unit) {
        return quantifiedTensoricFieldUsage.calculate(value, unit);
    }

    public final QuantifiedValue<V> sumOfQ(Iterable<QuantifiedValue<V>> values) {
        return quantifiedTensoricFieldUsage.sumOf(values);
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

}
