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

package org.tensorics.core.quantity.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.OperandPair;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Base class for operations on physical quantities, that can perform sum or difference, depending on the scalar
 * operation that is passed into the constructor. The left and right operand will be converted to the same base (if they
 * are not provided like this). The errors and validity flags will be propagated according to corresponding strategies
 * given in the environment.
 *
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based on
 */
public class QuantitySumOrDifferenceOperation<S> extends QuantityBinaryOperation<S> {
    private static final long serialVersionUID = 1L;

    public QuantitySumOrDifferenceOperation(QuantityEnvironment<S> environment, BinaryOperation<S> operation) {
        super(environment, operation);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        OperandPair<S, Unit> pair = environment().quantification().asSameUnit(left, right);
        Optional<S> error = environment().errorPropagation().errorForSumAndDifference(pair.left(), pair.right());
        S value = operation().perform(pair.left().value(), pair.right().value());
        boolean validity = validityFor(left, right);
        Unit unit = pair.unit();
        return Tensorics.quantityOf(value, unit).withError(error).withValidity(validity);
    }

}
