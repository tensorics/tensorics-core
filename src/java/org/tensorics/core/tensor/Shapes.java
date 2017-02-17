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

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Collections2.transform;
import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.collect.Iterables;
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
     * the dimensions of the two shapes are compatible. If they are not, then an {@link IllegalArgumentException} is
     * thrown.
     * 
     * @param left the first shape from which to take positions
     * @param right the second shape from which to take positions
     * @return a shape containing all positions, which are contained in both given shapes
     */
    public static Shape intersection(Shape left, Shape right) {
        return combineLeftRightBy(left, right, Sets::intersection);
    }

    /**
     * Creates a shape, containing all the positions that are either contained in the left or the right shape.This only
     * makes sense, if the dimensions of the two shapes are the same. If they are not, then an
     * {@link IllegalArgumentException} is thrown.
     * 
     * @param left the first shape from which to take positions
     * @param right the second shape from which to take positions
     * @return a shape containing all positions, which are contained in at least one of the two shapes
     */
    public static Shape union(Shape left, Shape right) {
        return combineLeftRightBy(left, right, Sets::union);
    }

    /**
     * Creates a shape, containing all the positions that are contained at least in one of the given shapes. This only
     * makes sense, if the dimensions of the shapes are compatible. If they are not, then an
     * {@link IllegalArgumentException} is thrown. Further, it is required that at least one element is contained in the
     * iterable.
     * 
     * @param shapes the shapes for which the union shall be found
     * @return a shape which represents the union of all the shapes
     * @throws IllegalArgumentException if the shapes are not of the same dimension
     * @throws NoSuchElementException in case the iterable is empty
     * @throws NullPointerException if the given iterable is {@code null}
     */
    public static final Shape union(Iterable<Shape> shapes) {
        return combineBy(shapes, Shapes::union);
    }

    /**
     * Creates a shape, containing the positions which are contained in each of the given shapes. This only makes sense,
     * if the dimensions of the shapes are the same. If they are not, then an {@link IllegalArgumentException} is
     * thrown. Further, it is required that at least one element is contained in the iterable.
     * 
     * @param shapes the shapes for which the intersection shall be found
     * @return a shape which represents the intersection of all the shapes
     * @throws IllegalArgumentException if the shapes are not of the same dimension
     * @throws NoSuchElementException in case the iterable is empty
     * @throws NullPointerException if the given iterable is {@code null}
     */
    public static final Shape intersection(Iterable<Shape> shapes) {
        return combineBy(shapes, Shapes::intersection);
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

    public static Set<Class<?>> parentDimensionalIntersection(Shape left, Shape right) {
        checkLeftRightNotNull(left, right);
        return Coordinates.parentClassIntersection(left.dimensionSet(), right.dimensionSet());
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
        Set<Class<?>> newDimensions = Coordinates.parentClassDifference(shape.dimensionSet(), dimensionsToStrip);
        return Shape.of(newDimensions, Positions
                .unique(transform(shape.positionSet(), toGuavaFunction(Positions.stripping(dimensionsToStrip)))));
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
        Shape.Builder builder = Shape.builder(Sets.union(left.dimensionSet(), right.dimensionSet()));
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

    private static <T, R> com.google.common.base.Function<T, R> toGuavaFunction(Function<T, R> function) {
        return new com.google.common.base.Function<T, R>() {

            @Override
            public R apply(T input) {
                return function.apply(input);
            }
        };
    }

    private static Shape combineLeftRightBy(Shape left, Shape right,
            BiFunction<Set<Position>, Set<Position>, Set<Position>> combiner) {
        checkLeftRightNotNull(left, right);
        if (left.dimensionality() != right.dimensionality()) {
            throw new IllegalArgumentException("Left and right shape do not have the same dimensionality!");
        }
        Set<Class<?>> dimensionalIntersection = Coordinates.parentClassIntersection(left.dimensionSet(), right.dimensionSet());
        if (dimensionalIntersection.size() != left.dimensionality()) {
            throw new IllegalArgumentException("The shapes are not compatible!");
        }
        return Shape.of(dimensionalIntersection, combiner.apply(left.positionSet(), right.positionSet()));
    }

    private static Shape combineBy(Iterable<Shape> shapes, BiFunction<Shape, Shape, Shape> combiner) {
        requireNonNull(shapes, "shapes must not be null");
        if (Iterables.isEmpty(shapes)) {
            throw new NoSuchElementException("At least one shape is required.");
        }
        Shape resultingShape = null;
        for (Shape shape : shapes) {
            if (resultingShape == null) {
                resultingShape = shape;
            } else {
                resultingShape = combiner.apply(resultingShape, shape);
            }
        }
        return resultingShape;
    }

}
