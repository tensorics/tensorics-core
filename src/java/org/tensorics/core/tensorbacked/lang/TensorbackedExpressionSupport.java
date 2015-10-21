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
