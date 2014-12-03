/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

public class TCoordinate implements TestCoordinate {

    private final int coor;

    /**
     * Create test coordinae
     * 
     * @param coor value of Coordinate
     */
    private TCoordinate(int coor) {
        this.coor = coor;
    }

    public static TCoordinate of(int coor) {
        return new TCoordinate(coor);
    }

    public int getValue() {
        return coor;
    }

    @Override
    public String toString() {
        return "t=[" + coor + "]";
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
        TCoordinate other = (TCoordinate) obj;
        if (coor != other.coor) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TestCoordinate o) {
        if (o.getClass().equals(this.getClass())) {
            return this.coor - ((TCoordinate) o).getValue();
        }
        throw new IllegalArgumentException("Cannot compare two coordinates of different Type [" + this.getClass()
                + " : " + o.getClass() + "]");
    }
}
