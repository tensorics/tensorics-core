/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;


/**
 * @author agorzaws
 */
public class XCoordinate implements TestCoordinate {

    private final int coor;

    /**
     * Create test coordinae
     * 
     * @param coor value of Coordinate
     */
    private XCoordinate(int coor) {
        this.coor = coor;
    }

    public static XCoordinate of(int coor) {
        return new XCoordinate(coor);
    }

    public int getValue() {
        return coor;
    }

    @Override
    public String toString() {
        return "x=[" + coor + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + coor;
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
        XCoordinate other = (XCoordinate) obj;
        if (coor != other.coor) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TestCoordinate o) {
        if (o.getClass().equals(this.getClass())) {
            return this.coor - ((XCoordinate) o).getValue();
        }
        throw new IllegalArgumentException("Cannot compare two coordinates of different Type [" + this.getClass()
                + " : " + o.getClass() + "]");
    }
}