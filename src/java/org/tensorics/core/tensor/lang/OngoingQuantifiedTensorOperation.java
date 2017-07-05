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

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;

/**
 * Part of the tensorics fluent API that provides methods to describe the right hand part of binary operations on
 * tensors containing quantified values.
 * 
 * @author kaifox
 * @param <S> the type of the scalars (elements of the field on which all the operations are based on)
 */
public class OngoingQuantifiedTensorOperation<S>
        implements OngoingOperation<Tensor<QuantifiedValue<S>>, QuantifiedValue<S>> {

    private final QuantityOperationRepository<S> operationRepository;
    private final Tensor<QuantifiedValue<S>> left;

    public OngoingQuantifiedTensorOperation(QuantityOperationRepository<S> operationRepository,
            Tensor<QuantifiedValue<S>> left) {
        super();
        this.operationRepository = operationRepository;
        this.left = left;
    }

    @Override
    public Tensor<QuantifiedValue<S>> plus(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.addition());
    }

    @Override
    public Tensor<QuantifiedValue<S>> minus(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.subtraction());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementTimes(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.multiplication());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementTimesV(QuantifiedValue<S> right) {
        return elementTimes(Tensorics.scalarOf(right));
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementDividedBy(Tensor<QuantifiedValue<S>> right) {
        return evaluate(right, operationRepository.division());
    }

    @Override
    public Tensor<QuantifiedValue<S>> elementDividedByV(QuantifiedValue<S> value) {
        Tensor<QuantifiedValue<S>> right = Tensorics.scalarOf(value);
        return elementDividedBy(right);
    }

    private Tensor<QuantifiedValue<S>> evaluate(Tensor<QuantifiedValue<S>> right,
            BinaryOperation<QuantifiedValue<S>> operation) {
        return new ElementBinaryOperation<>(operation, operationRepository.environment().options()).perform(left,
                right);
    }
}
