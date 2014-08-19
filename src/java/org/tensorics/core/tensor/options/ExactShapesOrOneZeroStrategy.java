/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.options;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Implementation of {@link ShapingStrategy} that checks if for two given {@link Tensor} objects shapes are exactly
 * the same (type of the coordinates, number of dimensions and coordinates instances). Otherwise it throws and
 * IllegalArgumentException.
 * 
 * @author agorzaws
 */
public class ExactShapesOrOneZeroStrategy implements ShapingStrategy {

    @Override
    public <C> Shape shapeLeftRight(Tensor<?> first, Tensor<?> second) {
        Shape shapeOfFirst = first.shape();
        Shape shapeOfSecond = second.shape();
        if (shapeOfFirst.equals(shapeOfSecond)) {
            return shapeOfFirst;
        }

        if (second.shape().dimensionality() == 0 && first.shape().dimensionality() > 0) {
            return first.shape();
        }

        if (first.shape().dimensionality() == 0 && second.shape().dimensionality() > 0) {
            return second.shape();
        }

        throw new IllegalArgumentException("Given tensors have different SHAPES. Cannot perform " + this.getClass()
                + " multiplication with them.");
    }

    @Override
    public Class<ShapingStrategy> getMarkerInterface() {
        return ShapingStrategy.class;
    }
}
