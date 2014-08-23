/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * @author agorzaws
 */
public class YCoordinate implements TestCoordinate {

    private static Interner<YCoordinate> INTERNER = Interners.newWeakInterner();

    private final int coor;

    /**
     * Create test coordinate
     * 
     * @param coor value of Coordinate
     */
    private YCoordinate(int coor) {
        this.coor = coor;
    }

    public static YCoordinate of(int coor) {
        return new YCoordinate(coor);
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