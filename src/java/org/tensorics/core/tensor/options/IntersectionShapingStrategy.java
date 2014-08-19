/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.options;

import static org.tensorics.core.tensor.Shapes.intersection;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * A strategy for shaping tensors resulting from binary (element wise) operations, which just takes the intersection of
 * both shapes. This means that the resulting shape will be such, that it will only contain positions which are
 * contained in both incoming shapes.
 * <p>
 * Example: <blockquote>
 * 
 * <pre>
 * Shape result = shapeLeftRight(Shape.of(POS_A, POS_B), Shape.of(POS_B, POS_C));
 * result.equals(Shape.of(POS_B)); // would be true
 * </pre>
 * 
 * </blockquote>
 * 
 * @author agorzaws, kfuchsbe
 */
public class IntersectionShapingStrategy implements ShapingStrategy {

    @Override
    public <C> Shape shapeLeftRight(Tensor<?> left, Tensor<?> right) {
        return intersection(left.shape(), right.shape());
    }

    @Override
    public Class<ShapingStrategy> getMarkerInterface() {
        return ShapingStrategy.class;
    }
}
