/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.tensorics.core.tensor.Tensor;

/**
 * An abstract interpolation implementation that provides the basic functions like sorting the {@link Comparable}
 * coordinate and finding the PREVIOUS/NEXT instances in the coordinates.
 * 
 * @author agorzaws
 * @param <C> type of the coordinate, must be {@link Comparable}
 * @param <V> type of the value in the {@link Tensor}
 */
public abstract class AbstractInterpolationStrategy<C extends Comparable<C>, V> implements InterpolationStrategy<C, V> {

    /**
     * Extracts the ordered list of the comparable coordinate along which the interpolation will be done
     * 
     * @param tensorWithTheOnlyOneCoordinateOfC tensor with only ONE coordinate
     * @param coordineteToInterpolate the coordinate to extract
     * @return an ordered (ascending) list of the comparable coordinates
     */
    protected List<C> getOrderedListOfComparableCoodrinate(Tensor<V> tensorWithTheOnlyOneCoordinateOfC,
            C coordineteToInterpolate) {

        checkIfOnlyCoordinatesOfTypeCAreIn(tensorWithTheOnlyOneCoordinateOfC, coordineteToInterpolate);

        /* we know that slice is the class of C */
        @SuppressWarnings("unchecked")
        Set<C> coordinatesOfType = (Set<C>) tensorWithTheOnlyOneCoordinateOfC.shape().coordinatesOfType(
                coordineteToInterpolate.getClass());

        List<C> orderedList = new ArrayList<C>(coordinatesOfType);
        Collections.sort(orderedList);
        return orderedList;
    }

    private void checkIfOnlyCoordinatesOfTypeCAreIn(Tensor<V> tensor, C coordineteToInterpolate) {

        boolean contains = tensor.shape().dimensionSet().contains(coordineteToInterpolate.getClass());
        int dimensionality = tensor.shape().dimensionality();
        int entries = tensor.shape().size();
        if (dimensionality != 1 && entries > 0 && contains) {
            Set<?> coordinates = tensor.shape().positionSet().iterator().next().coordinates();
            // TODO how to check if the only coordinate in the position is the class of C ?
            throw new IllegalStateException("Cannot perform interpolation in the tensor of more that 1 dimension "
                    + "or the given dimension is not of the correct class.");
        }
    }

    /**
     * Finds the comparable coordinate before of after the slice position.
     * 
     * @param orderedList an ordered list of coordinates
     * @param referencePosition
     * @param indexMove if 0 then the PREVIOUS will be returned, if +1 the NEXT will be returned.
     * @return the comparable coordinate BEFORE or AFTER the reference one.
     */
    protected C findIndex(List<C> orderedList, C referencePosition, int indexMove) {
        if (indexMove != 0 && indexMove != 1) {
            throw new IllegalArgumentException("Only 0 and +1 are allowed as indexMove parameters");
        }
        int indexToReturn = -1;
        for (C one : orderedList) {
            if (one.compareTo(referencePosition) < 0) {
                indexToReturn++;
            } else {
                break;
            }
        }

        if (indexToReturn < 0) {
            indexToReturn = 0;
        } else if (indexToReturn > orderedList.size() - 2) {
            indexToReturn = orderedList.size() - 2;
        }
        return orderedList.get(indexToReturn + indexMove);
    }

}
