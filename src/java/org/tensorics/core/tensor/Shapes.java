/**
 * CopyrOsight (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Sets.union;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * Contains utility methods to deal with tensor shapes.
 * 
 * @author kfuchsbe
 */
public final class Shapes {

    /**
     * Private constructor to avoid instantiation
     */
    private Shapes() {
        /* Only static methods */
    }

    /**
     * Creates a shape, containing all the positions that are contained in both given shapes. This only makes sense, if
     * the dimensions of the two sets are the same. If they are not, then an {@link IllegalArgumentException} is thrown.
     * 
     * @param left the first shape from which to take positions
     * @param right the second shape from which to take positions
     * @return a shape containing all positions, which are contained in both given shapes
     */
    public static Shape intersection(Shape left, Shape right) {
        checkLeftRightNotNull(left, right);
        if (!left.hasSameDimensionsAs(right)) {
            throw new IllegalArgumentException("The two shapes do not have the same dimension, "
                    + "therefore the intersection of coordinates cannot be determined. Left dimensions: "
                    + left.dimensionSet() + "; Right dimensions: " + right.dimensionSet());
        }
        return Shape.of(Sets.intersection(left.positionSet(), right.positionSet()));
    }

    /**
     * Extracts those dimensions, which are contained in both shapes.
     * 
     * @param left the first shape for which to look at the dimensions
     * @param right the second shape where to look at the dimensions.
     * @return all the dimensions which are present in both shapes
     */
    public static Set<Class<?>> dimensionalIntersection(Shape left, Shape right) {
        checkLeftRightNotNull(left, right);
        return Sets.intersection(left.dimensionSet(), right.dimensionSet());
    }

    /**
     * Constructs a shape that contains positions, which are derived from the positions of the given shape by stripping
     * (removing) the given dimensions. The returned shape contains then only unique resulting position-parts and thus
     * might be much smaller than the original shape.
     * 
     * @param shape the shape from which to extract the stripped positions
     * @param dimensionsToStrip the dimensions which shall be removed from the positions of the shape
     * @return a shape containing unique positions, resulting from the shapes positions, stripping the given dimensions
     * @throws NullPointerException if one of the arguments are null
     */
    public static Shape dimensionStripped(Shape shape, Set<? extends Class<?>> dimensionsToStrip) {
        checkNotNull(shape, "shape must not be null");
        checkNotNull(dimensionsToStrip, "dimensions must not be null");
        return Shape.of(Positions.unique(transform(shape.positionSet(), Positions.stripping(dimensionsToStrip))));
    }

    /**
     * Constructs a shape that contains all positions resulting from the outer product of the positions of the left
     * shape with those of the right shape. It is required that the two shapes have no overlap of dimensions (i.e. none
     * of the dimensions in the left shape are available in the right and vice versa).
     * 
     * @param left the left shape to use for the outer product
     * @param right the right shape to use for the outer product
     * @return a shape containing positions constructed from all combinations of the left positions with the right
     *         positions.
     * @throws NullPointerException if one of the arguments is null
     * @throws IllegalArgumentException if the two shapes have overlapping dimensions
     */
    public static Shape outerProduct(Shape left, Shape right) {
        checkArgument(dimensionalIntersection(left, right).isEmpty(), "The two shapes have "
                + "overlapping dimensions. The outer product is not foreseen to be used in this situation.");
        Shape.Builder builder = Shape.builder(union(left.dimensionSet(), right.dimensionSet()));
        for (Position leftPosition : left.positionSet()) {
            for (Position rightPosition : right.positionSet()) {
                builder.add(Positions.union(leftPosition, rightPosition));
            }
        }
        return builder.build();
    }

    /**
     * Checks that both the given arguments are not null and throws exceptions in case they are.
     * 
     * @param left the left shape to check for non-nullity
     * @param right the right shape to check for non-nullity
     * @throws NullPointerException if one of the arguments is {@code null} Fs
     */
    private static void checkLeftRightNotNull(Shape left, Shape right) {
        checkNotNull(left, "left shape must not be null");
        checkNotNull(right, "right shape must not be null");
    }

}
