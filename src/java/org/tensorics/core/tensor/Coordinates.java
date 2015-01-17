/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    /**
     * Provides the way to reduce long classpath names of the coordinates classes to only short Class names. It produces
     * a combination of the tensor and it's context result.
     * 
     * @param tensor to extract the dimension set and reduce their length of the output string
     * @return a reduced string
     */
    public static String dimensionsWithoutClassPath(Tensor<?> tensor) {
        String dimensions = dimensionsWithoutClassPath(tensor.shape().dimensionSet());
        String dimensionsContext = dimensionsWithoutClassPath(tensor.context().getPosition());
        return "Tensor:" + dimensions + ", Context:" + dimensionsContext;
    }

    /**
     * Provides the way to reduce long classpath names of the coordinates classes to only short Class names.
     * 
     * @param position to extract the dimension set and reduce their ength of the output string
     * @return a reduced string
     */
    public static String dimensionsWithoutClassPath(Position position) {
        return dimensionsWithoutClassPath(position.dimensionSet());
    }

    /**
     * Provides the way to reduce long classpath names of the coordinates classes to only short Class names.
     * 
     * @param dimensionSet to reduce length of the output string
     * @return a reduced string
     */
    public static String dimensionsWithoutClassPath(Set<Class<?>> dimensionSet) {
        List<String> classNames = new ArrayList<String>();
        for (Class<?> oneClass : dimensionSet) {
            classNames.add(oneClass.getSimpleName());
        }
        return classNames.toString();
    }

}
