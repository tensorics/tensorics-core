/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoordinateRange {
    private final Collection<XCoordinate> xCoordinates;
    private final Collection<YCoordinate> yCoordinates;
    private final Collection<ZCoordinate> zCoordinates;

    private CoordinateRange(Collection<XCoordinate> xCoordinates, Collection<YCoordinate> yCoordinates,
            Collection<ZCoordinate> zCoordinates) {
        super();
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.zCoordinates = zCoordinates;
    }

    public static CoordinateRange fromSize(TensorSize size) {
        List<XCoordinate> xcoords = new ArrayList<>();
        for (int i = 0; i < size.getNx(); i++) {
            xcoords.add(XCoordinate.of(i));
        }
        List<YCoordinate> ycoords = new ArrayList<>();
        for (int j = 0; j < size.getNy(); j++) {
            ycoords.add(YCoordinate.of(j));
        }
        List<ZCoordinate> zcoords = new ArrayList<>();
        for (int k = 0; k < size.getNz(); k++) {
            zcoords.add(ZCoordinate.of(k));
        }
        return new CoordinateRange(xcoords, ycoords, zcoords);
    }

    public Collection<XCoordinate> getxCoordinates() {
        return xCoordinates;
    }

    public Collection<YCoordinate> getyCoordinates() {
        return yCoordinates;
    }

    public Collection<ZCoordinate> getzCoordinates() {
        return zCoordinates;
    }
}