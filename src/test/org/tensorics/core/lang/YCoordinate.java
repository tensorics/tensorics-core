/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

/**
 * @author agorzaws
 */
public class YCoordinate implements TestCoordinate {
    private final int coor;

    /**
     * Create test coordinate
     * 
     * @param coor value of Coordinate
     */
    public YCoordinate(int coor) {
        this.coor = coor;
    }

    public int getValue() {
        return coor;
    }

    @Override
    public String toString() {
        return "y=[" + coor + "]";
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
        YCoordinate other = (YCoordinate) obj;
        if (coor != other.coor) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TestCoordinate o) {
        if (o.getClass().equals(this.getClass())) {
            return this.coor - ((YCoordinate) o).getValue();
        }
        throw new IllegalArgumentException("Cannot compare two coordinates of different Type [" + this.getClass()
                + " : " + o.getClass() + "]");
    }

}