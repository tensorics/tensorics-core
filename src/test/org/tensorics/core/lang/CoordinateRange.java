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