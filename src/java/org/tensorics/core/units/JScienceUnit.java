/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.units;

/**
 * Encapsulates the implementation of unit by the use of units of the jscience library (V 4.3)
 * 
 * @author kfuchsbe
 */
public final class JScienceUnit implements Unit {
    private static final long serialVersionUID = 1L;

    private final javax.measure.unit.Unit<?> unit;

    private JScienceUnit(javax.measure.unit.Unit<?> unit) {
        this.unit = unit;
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static JScienceUnit of(javax.measure.unit.Unit<?> unit) {
        return new JScienceUnit(unit);
    }

    public javax.measure.unit.Unit<?> getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return this.unit.toString();
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
        JScienceUnit other = (JScienceUnit) obj;
        if (unit == null) {
            if (other.unit != null) {
                return false;
            }
        } else if (!unit.equals(other.unit)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        return result;
    }

}
