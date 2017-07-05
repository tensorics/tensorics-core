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
    public static <V> Expression<Tensor<V>> elementwise(BinaryOperation<V> operation, // NOSONAR
                                                                                      // (too
                                                                                      // many
                                                                                      // parameters)
            Expression<Tensor<V>> leftTensor, Expression<Tensor<V>> right, OptionRegistry<ManipulationOption> options) {
        ElementBinaryOperation<V> elementQuantifiedBinaryOperation = new ElementBinaryOperation<>(operation, options);
        return new BinaryOperationExpression<>(elementQuantifiedBinaryOperation, leftTensor, right);
    }
}
