/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.expressions;

import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.operations.TensorExtractionOperation;
import org.tensorics.core.tensorbacked.operations.TensorWrappingOperation;
import org.tensorics.core.tree.domain.Expression;

/**
 * provides factory methods for expressions related to tensor backed operations.
 * 
 * @author kfuchsbe
 */
public final class TensorbackedExpressions {

    /**
     * Private constructor to avoid instantiation
     */
    private TensorbackedExpressions() {
        /* Only static methods */
    }

    public static <V, TB extends Tensorbacked<V>> Expression<Tensor<V>> extracted(Expression<TB> tensorbacked) {
        return new ConversionOperationExpression<>(new TensorExtractionOperation<V, TB>(), tensorbacked);
    }

    public static <V, TB extends Tensorbacked<V>> Expression<TB> wrapped(Class<TB> resultType,
            Expression<Tensor<V>> tensor) {
        return new ConversionOperationExpression<>(new TensorWrappingOperation<>(resultType), tensor);
    }

}
