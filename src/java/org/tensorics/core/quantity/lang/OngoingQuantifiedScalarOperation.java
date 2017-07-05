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

package org.tensorics.core.quantity.lang;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Part of the tensoric fluent API that provides methods to describe the right hand part of binary operations on
 * quantified scalars.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the field on which all the operations are based on)
 */
public class OngoingQuantifiedScalarOperation<S> {

    private final QuantifiedValue<S> left;
    private final QuantityOperationRepository<S> pseudoField;

    public OngoingQuantifiedScalarOperation(QuantifiedValue<S> left, QuantityOperationRepository<S> pseudoField) {
        super();
        this.left = left;
        this.pseudoField = pseudoField;
    }

    public QuantifiedValue<S> plus(S rightValue, Unit rightUnit) {
        return plus(Tensorics.quantityOf(rightValue, rightUnit));
    }

    public QuantifiedValue<S> minus(S rightValue, Unit rightUnit) {
        return minus(Tensorics.quantityOf(rightValue, rightUnit));
    }

    public QuantifiedValue<S> plus(S rightValue, javax.measure.unit.Unit<?> rightUnit) {
        return plus(Tensorics.quantityOf(rightValue, JScienceUnit.of(rightUnit)));
    }

    public QuantifiedValue<S> minus(S rightValue, javax.measure.unit.Unit<?> rightUnit) {
        return minus(Tensorics.quantityOf(rightValue, JScienceUnit.of(rightUnit)));
    }

    /*
     * methods using our scalars
     */

    public QuantifiedValue<S> plus(QuantifiedValue<S> right) {
        return pseudoField.addition().perform(left, right);
    }

    public QuantifiedValue<S> minus(QuantifiedValue<S> right) {
        return pseudoField.subtraction().perform(left, right);
    }

    public QuantifiedValue<S> times(S rightValue, javax.measure.unit.Unit<?> unit) {
        return times(Tensorics.quantityOf(rightValue, JScienceUnit.of(unit)));
    }

    public QuantifiedValue<S> dividedBy(S rightValue, javax.measure.unit.Unit<?> unit) {
        return dividedBy(Tensorics.quantityOf(rightValue, JScienceUnit.of(unit)));
    }

    public QuantifiedValue<S> times(QuantifiedValue<S> right) {
        return pseudoField.multiplication().perform(left, right);
    }

    public QuantifiedValue<S> dividedBy(QuantifiedValue<S> right) {
        return pseudoField.division().perform(left, right);
    }

    public QuantifiedValue<S> toThePowerOf(QuantifiedValue<S> rightValue) {
        return pseudoField.power().perform(left, rightValue);
    }

    public QuantifiedValue<S> root(QuantifiedValue<S> rightValue) {
        return pseudoField.root().perform(left, rightValue);
    }

}
