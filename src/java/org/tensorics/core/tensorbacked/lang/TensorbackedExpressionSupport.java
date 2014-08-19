/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.operations.ElementTensorBackedUnaryOperation;
import org.tensorics.core.tree.domain.Expression;

/**
 * Provides methods to perform calculations on expressions of tensorbacked objects.
 * 
 * @author kfuchsbe, agorzaws
 * @param <V> the type of the scalar values (elements of the field on which all operations are based on)
 */
public class TensorbackedExpressionSupport<V> {

    private final Environment<V> environment;

    public TensorbackedExpressionSupport(Environment<V> environment) {
        this.environment = environment;
    }

    public final <TB extends Tensorbacked<V>> Expression<TB> elementNegativeOfTB(Expression<TB> tensorbacked) {
        return new UnaryOperationExpression<>(new ElementTensorBackedUnaryOperation<V, TB>(environment.field()
                .additiveInversion()), tensorbacked);
    }

    public final <TB extends Tensorbacked<V>> OngoingDeferredTensorBackedOperation<V, TB> calculateTB(
            Class<TB> resultClass, Expression<TB> left) {
        return new OngoingDeferredTensorBackedOperation<V, TB>(environment, resultClass, left);
    }

}
