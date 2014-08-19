/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import javax.measure.converter.UnitConverter;

import org.tensorics.core.math.Cheating;
import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.quantity.ImmutableErronousValue;
import org.tensorics.core.quantity.Quantified;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Encapsulates all the calculations and transformations of units that are based on the jscience unit system. Currently,
 * this is the only implemented way of dealing with units in tensorics. However, later on it is planned that all the
 * unit calculations could be based on the field structure, so that this class would get obsolete.
 * 
 * @author kfuchsbe
 * @param <T> the type of the field elements, on which the calculations are based on
 */
public class JScienceQuantificationStrategy<T> implements QuantificationStrategy<T> {

    private static final Unit UNIT_ONE = JScienceUnit.of(javax.measure.unit.Unit.ONE);

    private final Cheating<T> cheating;

    public JScienceQuantificationStrategy(Cheating<T> cheating) {
        super();
        this.cheating = cheating;
    }

    @Override
    public <S extends ErronousValue<T> & Quantified> OperandPair<T, Unit> asSameUnit(S left, S right) {
        if (left.unit().equals(right.unit())) {
            return OperandPair.ofLeftRightUnit(left, right, left.unit());
        } else {
            return OperandPair.ofLeftRightUnit(toStandardUnit(left), toStandardUnit(right), standardUnitOf(left));
        }
    }

    private <S extends Quantified> Unit standardUnitOf(S scalar) {
        Unit unit = scalar.unit();
        throwIfNotJScience(unit);
        return JScienceUnit.of(((JScienceUnit) unit).getUnit().getStandardUnit());
    }

    private <S extends ErronousValue<T> & Quantified> ErronousValue<T> toStandardUnit(S scalar) {
        Unit unit = scalar.unit();
        throwIfNotJScience(unit);
        UnitConverter converter = extract(unit).toStandardUnit();
        T value = convert(converter, scalar.value());
        if (scalar.error().isPresent()) {
            return ImmutableErronousValue.ofValueAndError(value, convert(converter, scalar.error().get()));
        }
        return ImmutableErronousValue.ofValue(value);
    }

    private T convert(UnitConverter converter, T value) {
        return cheating.fromDouble(converter.convert(cheating.toDouble(value)));
    }

    @Override
    public Unit multiply(Unit left, Unit right) {
        throwIfNotJScience(left, right);
        return JScienceUnit.of(extract(left).times(extract(right)));
    }

    @Override
    public Unit divide(Unit left, Unit right) {
        throwIfNotJScience(left, right);
        return JScienceUnit.of(extract(left).divide(extract(right)));
    }

    private javax.measure.unit.Unit<?> extract(Unit unit) {
        return ((JScienceUnit) unit).getUnit();
    }

    private void throwIfNotJScience(Unit... units) {
        for (Unit unit : units) {
            if (!(unit instanceof JScienceUnit)) {
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        }
    }

    @Override
    public Unit one() {
        return UNIT_ONE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<QuantificationStrategy> getMarkerInterface() {
        return QuantificationStrategy.class;
    }

}
