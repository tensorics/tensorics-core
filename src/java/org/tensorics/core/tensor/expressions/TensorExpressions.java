/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.expressions;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.CreationOperationExpression;
import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensor.operations.SingleValueTensorCreationOperation;
import org.tensorics.core.tree.domain.Expression;

/**
 * Provides factory methods for tensor expressions
 * 
 * @author kfuchsbe
 */
public final class TensorExpressions {

    /**
     * Private constructor to avoid instantiation
     */
    private TensorExpressions() {
        /* Only static methods */
    }

    public static <V> Expression<Tensor<V>> elementwise(UnaryOperation<V> operation, Expression<Tensor<V>> tensor) {
        return new UnaryOperationExpression<>(new ElementUnaryOperation<V>(operation), tensor);
    }

    public static <V> CreationOperationExpression<Tensor<V>> creationOfShapeValue(Shape shape, V value) {
        return new CreationOperationExpression<>(new SingleValueTensorCreationOperation<>(shape, value));
    }

    /* Not nice too have four parameters here, could be refactored */
    public static <V> Expression<Tensor<V>> elementwise(BinaryOperation<V> operation, // NOSONAR (too many parameters)
            Expression<Tensor<V>> leftTensor, Expression<Tensor<V>> right, OptionRegistry<ManipulationOption> options) {
        ElementBinaryOperation<V> elementQuantifiedBinaryOperation = new ElementBinaryOperation<>(operation, options);
        return new BinaryOperationExpression<>(elementQuantifiedBinaryOperation, leftTensor, right);
    }
}
