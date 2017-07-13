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

package org.tensorics.core.quantity.options;

import java.io.Serializable;

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
public class JScienceQuantificationStrategy<T> implements QuantificationStrategy<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Unit UNIT_ONE = JScienceUnit.of(javax.measure.unit.Unit.ONE);

    private final Cheating<T> cheating;

    public JScienceQuantificationStrategy(Cheating<T> cheating) {
        this.cheating = cheating;
    }

    @Override
    public <S extends ErronousValue<T> & Quantified> OperandPair<T, Unit> asSameUnit(S left, S right) {
        if (left.unit().equals(right.unit())) {
            return OperandPair.ofLeftRightUnit(left, right, left.unit());
        } else {
            final Unit standardUnitOfLeft = standardUnitOf(left);
            final Unit standardUnitOfRight = standardUnitOf(right);
            if (!standardUnitOfLeft.equals(standardUnitOfRight)) {
                throw new IllegalArgumentException("Units '" + standardUnitOfLeft + "' and '" + standardUnitOfRight
                        + "' are not consistent or convertible");
            }
            return OperandPair.ofLeftRightUnit(toStandardUnit(left), toStandardUnit(right), standardUnitOfLeft);
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
        return convert(converter, scalar);
    }

    private <S extends ErronousValue<T> & Quantified> ImmutableErronousValue<T> convert(UnitConverter converter,
            S scalar) {
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

    @Override
    public <V extends ErronousValue<T> & Quantified> ErronousValue<T> convertValueToUnit(V value, Unit targetUnit) {
        throwIfNotJScience(value.unit(), targetUnit);
        UnitConverter converter = extract(value.unit()).getConverterTo(extract(targetUnit));
        return convert(converter, value);
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

    @Override
    public Unit root(Unit left, T right) {
        throwIfNotJScience(left);
        return JScienceUnit.of(extract(left).root((int) cheating.toDouble(right)));
    }

    @Override
    public Unit power(Unit left, T rigth) {
        throwIfNotJScience(left);
        return JScienceUnit.of(extract(left).pow((int) cheating.toDouble(rigth)));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cheating == null) ? 0 : cheating.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JScienceQuantificationStrategy other = (JScienceQuantificationStrategy) obj;
        if (cheating == null) {
            if (other.cheating != null) {
                return false;
            }
        } else if (!cheating.equals(other.cheating)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JScienceQuantificationStrategy [cheating=" + cheating + "]";
    }

}
