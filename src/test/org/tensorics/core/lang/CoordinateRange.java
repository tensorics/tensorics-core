/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.util.HashSet;
import java.util.Set;

public class CoordinateRange {
    private final Set<XCoordinate> xCoordinates;
    private final Set<YCoordinate> yCoordinates;
    private final Set<ZCoordinate> zCoordinates;

    private CoordinateRange(Set<XCoordinate> xCoordinates, Set<YCoordinate> yCoordinates, Set<ZCoordinate> zCoordinates) {
        super();
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.zCoordinates = zCoordinates;
    }

    public static CoordinateRange fromSize(TensorSize size) {
        Set<XCoordinate> xcoords = new HashSet<>();
        for (int i = 0; i < size.getNx(); i++) {
            xcoords.add(XCoordinate.of(i));
        }
        Set<YCoordinate> ycoords = new HashSet<>();
        for (int j = 0; j < size.getNy(); j++) {
            ycoords.add(YCoordinate.of(j));
        }
        Set<ZCoordinate> zcoords = new HashSet<>();
        for (int k = 0; k < size.getNz(); k++) {
            zcoords.add(ZCoordinate.of(k));
        }
        return new CoordinateRange(xcoords, ycoords, zcoords);
    }

    public Set<XCoordinate> getxCoordinates() {
        return xCoordinates;
    }

    public Set<YCoordinate> getyCoordinates() {
        return yCoordinates;
    }

    public Set<ZCoordinate> getzCoordinates() {
        return zCoordinates;
    }
}