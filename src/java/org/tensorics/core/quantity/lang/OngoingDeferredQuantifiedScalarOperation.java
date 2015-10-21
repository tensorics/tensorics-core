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

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Provides methods to describe the right hand part of binary operations on expressions of quantified values
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the field on which the operations are based)
 */
@SuppressWarnings("PMD.TooManyMethods")
public class OngoingDeferredQuantifiedScalarOperation<S> {

    private final Expression<QuantifiedValue<S>> left;
    private final QuantityOperationRepository<S> pseudoField;

    public OngoingDeferredQuantifiedScalarOperation(Expression<QuantifiedValue<S>> left,
            QuantityOperationRepository<S> pseudoField) {
        super();
        this.left = left;
        this.pseudoField = pseudoField;
    }

    public Expression<QuantifiedValue<S>> plus(S rightValue, Unit rightUnit) {
        return plus(Tensorics.quantityOf(rightValue, rightUnit));
    }

    public Expression<QuantifiedValue<S>> minus(S rightValue, Unit rightUnit) {
        return minus(Tensorics.quantityOf(rightValue, rightUnit));
    }

    public Expression<QuantifiedValue<S>> plus(S rightValue, javax.measure.unit.Unit<?> rightUnit) {
        return plus(Tensorics.quantityOf(rightValue, JScienceUnit.of(rightUnit)));
    }

    public Expression<QuantifiedValue<S>> minus(S rightValue, javax.measure.unit.Unit<?> rightUnit) {
        return minus(Tensorics.quantityOf(rightValue, JScienceUnit.of(rightUnit)));
    }

    /*
     * methods using our scalars
     */

    public Expression<QuantifiedValue<S>> plus(QuantifiedValue<S> right) {
        return plus(ResolvedExpression.of(right));
    }

    public Expression<QuantifiedValue<S>> plus(Expression<QuantifiedValue<S>> right) {
        return new BinaryOperationExpression<>(pseudoField.addition(), left, right);
    }

    public Expression<QuantifiedValue<S>> minus(QuantifiedValue<S> right) {
        return minus(ResolvedExpression.of(right));
    }

    public Expression<QuantifiedValue<S>> minus(Expression<QuantifiedValue<S>> right) {
        return new BinaryOperationExpression<>(pseudoField.subtraction(), left, right);
    }

    public Expression<QuantifiedValue<S>> times(S rightValue, javax.measure.unit.Unit<?> unit) {
        return times(Tensorics.quantityOf(rightValue, JScienceUnit.of(unit)));
    }

    public Expression<QuantifiedValue<S>> dividedBy(S rightValue, javax.measure.unit.Unit<?> unit) {
        return dividedBy(Tensorics.quantityOf(rightValue, JScienceUnit.of(unit)));
    }

    public Expression<QuantifiedValue<S>> times(QuantifiedValue<S> right) {
        return times(ResolvedExpression.of(right));
    }

    public Expression<QuantifiedValue<S>> times(Expression<QuantifiedValue<S>> right) {
        return new BinaryOperationExpression<>(pseudoField.multiplication(), left, right);
    }

    public Expression<QuantifiedValue<S>> dividedBy(QuantifiedValue<S> right) {
        return dividedBy(ResolvedExpression.of(right));
    }

    public Expression<QuantifiedValue<S>> dividedBy(Expression<QuantifiedValue<S>> right) {
        return new BinaryOperationExpression<>(pseudoField.division(), left, right);
    }

}
