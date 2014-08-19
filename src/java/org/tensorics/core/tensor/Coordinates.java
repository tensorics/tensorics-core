/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;

/**
 * Utility methods to handle coordinates
 * 
 * @author kfuchsbe
 */
public final class Coordinates {

    private Coordinates() {
        /* only static methods */
    }

    /**
     * Creates a class to instance map, from the given coordinates. The map will contain the classes of the coordinates
     * as keys and the coordinates themselves as values. Duplicate keys (dimensions) are not allowed and will result in
     * an {@link IllegalArgumentException}.
     * 
     * @param coordinates the coordinates to be added to the map
     * @return an immutable map from dimensions (coordinate classes) to coordinate
     * @throws IllegalArgumentException if more than one coordinate per dimension are provided
     */
    public static <C> ClassToInstanceMap<C> mapOf(Iterable<? extends C> coordinates) {
        ImmutableClassToInstanceMap.Builder<C> coordinateBuilder = ImmutableClassToInstanceMap.builder();
        for (C coordinate : coordinates) {
            @SuppressWarnings("unchecked")
            Class<C> coordinateClass = (Class<C>) coordinate.getClass();
            coordinateBuilder.put(coordinateClass, coordinate);
        }
        return coordinateBuilder.build();
    }

}
