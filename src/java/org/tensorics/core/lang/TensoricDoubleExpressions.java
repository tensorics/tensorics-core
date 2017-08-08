/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.util.Comparator;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.lang.FunctionExpressionSupportWithConversionAndComparator;
import org.tensorics.core.iterable.lang.OngoingDeferredIterableBinaryPredicate;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.OngoingDeferredQuantifiedScalarOperation;
import org.tensorics.core.scalar.lang.OngoingDeferredBinaryOperation;
import org.tensorics.core.scalar.lang.OngoingDeferredBinaryPredicate;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingDeferredQuantifiedTensorOperation;
import org.tensorics.core.tensor.lang.OngoingDeferredTensorOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.lang.OngoingDeferredQuantifiedTensorBackedOperation;
import org.tensorics.core.tensorbacked.lang.OngoingDeferredTensorBackedOperation;
import org.tensorics.core.tree.domain.Expression;

public class TensoricDoubleExpressions {

    private static final TensoricDoubleExpressionSupport EXPRESSION_SUPPORT = new TensoricDoubleExpressionSupport();

    public static final Expression<Double> negativeOf(Expression<Double> element) {
        return EXPRESSION_SUPPORT.negativeOf(element);
    }

    public final Expression<Double> averageOf(Iterable<Double> iterable) {
        return EXPRESSION_SUPPORT.averageOf(iterable);
    }

    public static final Expression<Double> inverseOf(Expression<Double> element) {
        return EXPRESSION_SUPPORT.inverseOf(element);
    }

    public static final Expression<Double> averageOf(Expression<? extends Iterable<Double>> iterableExpression) {
        return EXPRESSION_SUPPORT.averageOf(iterableExpression);
    }

    public static final Expression<Double> sizeOf(Expression<? extends Iterable<?>> iterableExpression) {
        return EXPRESSION_SUPPORT.sizeOf(iterableExpression);
    }

    public Expression<Double> squareRootOf(Expression<Double> value) {
        return EXPRESSION_SUPPORT.squareRootOf(value);
    }

    public static final Expression<Double> sumOf(Expression<? extends Iterable<Double>> iterableExpression) {
        return EXPRESSION_SUPPORT.sumOf(iterableExpression);
    }

    public Expression<Double> squareOf(Expression<Double> value) {
        return EXPRESSION_SUPPORT.squareOf(value);
    }

    public static final OngoingDeferredTensorOperation<Double> calculateT(Expression<Tensor<Double>> tensoric) {
        return EXPRESSION_SUPPORT.calculateT(tensoric);
    }

    public static final Expression<Double> rmsOf(Expression<? extends Iterable<Double>> iterableExpression) {
        return EXPRESSION_SUPPORT.rmsOf(iterableExpression);
    }

    public static final OngoingDeferredBinaryOperation<Double> calculate(Expression<Double> left) {
        return EXPRESSION_SUPPORT.calculate(left);
    }

    public static final Expression<Double> sumOfSquaresOf(Expression<? extends Iterable<Double>> iterableExpression) {
        return EXPRESSION_SUPPORT.sumOfSquaresOf(iterableExpression);
    }

    public static final Expression<Tensor<Double>> elementInverseOf(Expression<Tensor<Double>> tensor) {
        return EXPRESSION_SUPPORT.elementInverseOf(tensor);
    }

    public static final Expression<Tensor<Double>> elementNegativeOf(Expression<Tensor<Double>> tensor) {
        return EXPRESSION_SUPPORT.elementNegativeOf(tensor);
    }

    public static final OngoingDeferredQuantifiedTensorOperation<Double> calculateQT(
            Tensor<QuantifiedValue<Double>> left) {
        return EXPRESSION_SUPPORT.calculate(left);
    }

    public static final OngoingDeferredQuantifiedScalarOperation<Double> calculateQ(
            Expression<QuantifiedValue<Double>> scalar) {
        return EXPRESSION_SUPPORT.calculateQ(scalar);
    }

    public static final <TB extends Tensorbacked<Double>> Expression<TB> elementNegativeOfTB(Expression<TB> tensor) {
        return EXPRESSION_SUPPORT.elementNegativeOfTB(tensor);
    }

    public static final <TB extends Tensorbacked<Double>> OngoingDeferredTensorBackedOperation<Double, TB> calculateTB(
            Class<TB> resultClass, Expression<TB> tensoric) {
        return EXPRESSION_SUPPORT.calculateTB(resultClass, tensoric);
    }

    public static final <QTB extends Tensorbacked<QuantifiedValue<Double>>> Expression<QTB> elementNegativeOfQTB(
            Class<QTB> resultClass, Expression<QTB> tensor) {
        return EXPRESSION_SUPPORT.elementNegativeOfQTB(resultClass, tensor);
    }

    public static final <TB extends Tensorbacked<QuantifiedValue<Double>>> OngoingDeferredQuantifiedTensorBackedOperation<Double, TB> calculateQTB(
            Class<TB> resultClass, Expression<TB> tensor) {
        return EXPRESSION_SUPPORT.calculateQTB(resultClass, tensor);
    }

    public static final OngoingDeferredBinaryPredicate<Double> testIf(Expression<Double> expression) {
        return EXPRESSION_SUPPORT.testIf(expression);
    }

    public static final OngoingDeferredIterableBinaryPredicate<Double> testIfIt(
            Expression<Iterable<Double>> iterableExpression) {
        return EXPRESSION_SUPPORT.testIfIt(iterableExpression);
    }

    public static final Expression<Double> rmsOfF(Expression<DiscreteFunction<Double, Double>> functionExpresssion) {
        return EXPRESSION_SUPPORT.rmsOfF(functionExpresssion);
    }

    public static final Expression<Double> averageOfF(
            Expression<DiscreteFunction<Double, Double>> functionExpresssion) {
        return EXPRESSION_SUPPORT.averageOfF(functionExpresssion);
    }

    public static final <X> FunctionExpressionSupportWithConversionAndComparator<X, Double> withConversionAndComparator(
            Conversion<X, Double> conversion, Comparator<X> comparator) {
        return EXPRESSION_SUPPORT.withConversionAndComparator(conversion, comparator);
    }

}
