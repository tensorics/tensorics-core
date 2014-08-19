/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.OngoingDeferredQuantifiedScalarOperation;
import org.tensorics.core.scalar.lang.OngoingDeferredBinaryOperation;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingDeferredQuantifiedTensorOperation;
import org.tensorics.core.tensor.lang.OngoingDeferredTensorOperation;
import org.tensorics.core.tensor.lang.QuantityTensorExpressionSupport;
import org.tensorics.core.tensor.lang.TensorExpressionSupport;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.lang.OngoingDeferredQuantifiedTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.OngoingDeferredTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.QuantityTensorbackedExpressionSupport;
import org.tensorics.core.tensorbacked.lang.TensorbackedExpressionSupport;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.units.Unit;

/**
 * Mixes together the quantified versions of the operations and the basic tensor operations. Contains delegation methods
 * only!
 * 
 * @author kfuchsbe
 * @param <V> The type of the values of the tensors
 */
@SuppressWarnings("PMD.TooManyMethods")
public class TensoricExpressionSupport<V> {

    private final TensorExpressionSupport<V> tensoricFieldUsage;
    private final TensorbackedExpressionSupport<V> tensorbackedExpressionSupport;
    private final QuantityTensorExpressionSupport<V> quantifiedTensoricFieldUsage;
    private final QuantityTensorbackedExpressionSupport<V> quantityTensorbackedExpressionSupport;

    private final EnvironmentImpl<V> environment;

    public TensoricExpressionSupport(EnvironmentImpl<V> environment) {
        this.tensoricFieldUsage = new TensorExpressionSupport<>(environment);
        this.tensorbackedExpressionSupport = new TensorbackedExpressionSupport<>(environment);
        this.quantifiedTensoricFieldUsage = new QuantityTensorExpressionSupport<>(environment);
        this.quantityTensorbackedExpressionSupport = new QuantityTensorbackedExpressionSupport<>(environment);
        this.environment = environment;
    }

    public TensoricExpressionSupport<V> with(ManipulationOption newOption) {
        return new TensoricExpressionSupport<>(environment.with(newOption));
    }

    public final Expression<V> negativeOf(V element) {
        return tensoricFieldUsage.negativeOf(element);
    }

    public final Expression<V> negativeOf(Expression<V> element) {
        return tensoricFieldUsage.negativeOf(element);
    }

    public final Expression<V> inverseOf(V element) {
        return tensoricFieldUsage.inverseOf(element);
    }

    public final Expression<V> averageOf(Iterable<V> iterable) {
        return tensoricFieldUsage.averageOf(iterable);
    }

    public final Expression<V> inverseOf(Expression<V> element) {
        return tensoricFieldUsage.inverseOf(element);
    }

    public final Expression<V> averageOf(Expression<Iterable<V>> iterableExpression) {
        return tensoricFieldUsage.averageOf(iterableExpression);
    }

    public final V zero() {
        return tensoricFieldUsage.zero();
    }

    public final Expression<V> sizeOf(Iterable<V> iterable) {
        return tensoricFieldUsage.sizeOf(iterable);
    }

    public final V two() {
        return tensoricFieldUsage.two();
    }

    public final V one() {
        return tensoricFieldUsage.one();
    }

    public final Expression<V> sizeOf(Expression<Iterable<V>> iterableExpression) {
        return tensoricFieldUsage.sizeOf(iterableExpression);
    }

    public Expression<V> squareRootOf(V value) {
        return tensoricFieldUsage.squareRootOf(value);
    }

    public Expression<V> squareRootOf(Expression<V> value) {
        return tensoricFieldUsage.squareRootOf(value);
    }

    public final Expression<V> sumOf(Iterable<V> iterable) {
        return tensoricFieldUsage.sumOf(iterable);
    }

    public Expression<V> squareOf(V value) {
        return tensoricFieldUsage.squareOf(value);
    }

    public final Expression<V> sumOf(Expression<Iterable<V>> iterableExpression) {
        return tensoricFieldUsage.sumOf(iterableExpression);
    }

    public Expression<V> squareOf(Expression<V> value) {
        return tensoricFieldUsage.squareOf(value);
    }

    public final <C> OngoingDeferredTensorOperation<V> calculateT(Expression<Tensor<V>> tensoric) {
        return tensoricFieldUsage.calculateT(tensoric);
    }

    public Expression<V> rmsOf(Iterable<V> iterable) {
        return tensoricFieldUsage.rmsOf(iterable);
    }

    public OngoingDeferredBinaryOperation<V> calculate(V left) {
        return tensoricFieldUsage.calculate(left);
    }

    public Expression<V> rmsOf(Expression<Iterable<V>> iterableExpression) {
        return tensoricFieldUsage.rmsOf(iterableExpression);
    }

    public OngoingDeferredBinaryOperation<V> calculate(Expression<V> left) {
        return tensoricFieldUsage.calculate(left);
    }

    public Expression<V> sumOfSquaresOf(Iterable<V> iterable) {
        return tensoricFieldUsage.sumOfSquaresOf(iterable);
    }

    public final <C> Expression<Tensor<V>> zeros(Shape shape) {
        return tensoricFieldUsage.zeros(shape);
    }

    public Expression<V> sumOfSquaresOf(Expression<Iterable<V>> iterableExpression) {
        return tensoricFieldUsage.sumOfSquaresOf(iterableExpression);
    }

    public final <C> Expression<Tensor<V>> ones(Shape shape) {
        return tensoricFieldUsage.ones(shape);
    }

    public final <C> Expression<Tensor<V>> elementInverseOf(Expression<Tensor<V>> tensor) {
        return tensoricFieldUsage.elementInverseOf(tensor);
    }

    public final <C> Expression<Tensor<V>> elementNegativeOf(Expression<Tensor<V>> tensor) {
        return tensoricFieldUsage.elementNegativeOf(tensor);
    }

    public OngoingDeferredQuantifiedTensorOperation<V> calculate(Tensor<QuantifiedValue<V>> left) {
        return quantifiedTensoricFieldUsage.calculateV(left);
    }

    public Expression<QuantifiedValue<V>> valueOf(V value, Unit unit) {
        return quantifiedTensoricFieldUsage.valueOf(value, unit);
    }

    public OngoingDeferredQuantifiedScalarOperation<V> calculate(V value, javax.measure.unit.Unit<?> unit) {
        return quantifiedTensoricFieldUsage.calculate(value, unit);
    }

    public OngoingDeferredQuantifiedScalarOperation<V> calculateQ(Expression<QuantifiedValue<V>> scalar) {
        return quantifiedTensoricFieldUsage.calculate(scalar);
    }

    public Expression<QuantifiedValue<V>> negativeOf(QuantifiedValue<V> element) {
        return quantifiedTensoricFieldUsage.negativeOf(element);
    }

    public Expression<QuantifiedValue<V>> inverseOf(QuantifiedValue<V> element) {
        return quantifiedTensoricFieldUsage.inverseOf(element);
    }

    public Expression<QuantifiedValue<V>> valueOf(V value, javax.measure.unit.Unit<?> unit) {
        return quantifiedTensoricFieldUsage.valueOf(value, unit);
    }

    public final <TB extends Tensorbacked<V>> Expression<TB> elementNegativeOfTB(Expression<TB> tensor) {
        return tensorbackedExpressionSupport.elementNegativeOfTB(tensor);
    }

    public final <TB extends Tensorbacked<V>> OngoingDeferredTensorBackedOperation<V, TB> calculateTB(
            Class<TB> resultClass, Expression<TB> tensoric) {
        return tensorbackedExpressionSupport.calculateTB(resultClass, tensoric);
    }

    public final <QTB extends Tensorbacked<QuantifiedValue<V>>> Expression<QTB> elementNegativeOfQTB(
            Class<QTB> resultClass, Expression<QTB> tensor) {
        return quantityTensorbackedExpressionSupport.elementNegativeOfTB(resultClass, tensor);
    }

    public final <TB extends Tensorbacked<QuantifiedValue<V>>> OngoingDeferredQuantifiedTensorBackedOperation<V, TB> //
    calculateQTB(Class<TB> resultClass, Expression<TB> tensor) {
        return quantityTensorbackedExpressionSupport.calculateTB(resultClass, tensor);
    }

}
