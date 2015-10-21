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

package org.tensorics.core.tensor.lang;

import org.tensorics.core.iterable.lang.QuantityIterableExpressionSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides starting point methods for tensoric eDSL language expressions that describe expressions of tensors of
 * quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
public class QuantityTensorExpressionSupport<V> extends QuantityIterableExpressionSupport<V> {

    private final QuantityOperationRepository<V> quantityOperationRepository;

    public QuantityTensorExpressionSupport(QuantityEnvironment<V> environment) {
        super(environment);
        this.quantityOperationRepository = new QuantityOperationRepository<>(environment);
    }

    public OngoingDeferredQuantifiedTensorOperation<V> calculateV(Tensor<QuantifiedValue<V>> left) {
        return calculateT(ResolvedExpression.of(left));
    }

    public OngoingDeferredQuantifiedTensorOperation<V> calculateT(Expression<Tensor<QuantifiedValue<V>>> left) {
        return new OngoingDeferredQuantifiedTensorOperation<>(quantityOperationRepository, left);
    }

}
