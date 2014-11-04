/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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

}
