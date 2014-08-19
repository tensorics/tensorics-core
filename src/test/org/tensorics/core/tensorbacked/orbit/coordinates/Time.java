/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.orbit.coordinates;

public class Time implements OrbitCoordinate {
    private final long time;

    /**
     * @param time
     */
    public Time(long time) {
        super();
        this.time = time;
    }

    public long getTime() {
        return time;
    }

}
