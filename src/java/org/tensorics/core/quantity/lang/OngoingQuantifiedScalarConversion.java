/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.lang;

import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Part of a fluent clause to convert quantities into each other .
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values of the quantities
 */
public class OngoingQuantifiedScalarConversion<S> {

    private final QuantifiedValue<S> left;
    private final QuantificationStrategy<S> quantification;

    public OngoingQuantifiedScalarConversion(QuantifiedValue<S> left, QuantificationStrategy<S> quantification) {
        super();
        this.left = left;
        this.quantification = quantification;
    }

    public QuantifiedValue<S> to(Unit targetUnit) {
        ErronousValue<S> converted = quantification.convertValueToUnit(left, targetUnit);
        return ImmutableQuantifiedValue.of(converted.value(), targetUnit).withError(converted.error())
                .withValidity(left.validity());
    }

    public QuantifiedValue<S> to(javax.measure.unit.Unit<?> targetUnit) {
        return to(JScienceUnit.of(targetUnit));
    }

}
