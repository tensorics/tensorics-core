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

import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.tensorics.core.tensor.Shapes.dimensionStripped;
import static org.tensorics.core.tensor.Shapes.dimensionalIntersection;
import static org.tensorics.core.tensor.Shapes.intersection;
import static org.tensorics.core.tensor.Shapes.outerProduct;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class ShapesTest {

    private static final Position POS_A = Position.of("A");
    private static final Position POS_B = Position.of("B");
    private static final Position POS_C = Position.of("C");
    private static final Position POS_1 = Position.of(1);
    private static final Position POS_2 = Position.of(2);
    private static final Position POS_A1 = Position.of("A", 1);
    private static final Position POS_B1 = Position.of("B", 1);
    private static final Position POS_A2 = Position.of("A", 2);
    private static final Position POS_B2 = Position.of("B", 2);
    private static final Position POS_A01 = Position.of("A", 0.1);

    private Shape shapeA;
    private Shape shapeB;
    private Shape shapeAB;
    private Shape shapeBC;
    private Shape shape1;
    private Shape shape12;
    private Shape shapeA1B1A2B2;
    private Shape shapeA01;

    @Before
    public void setUp() {
        shapeA = Shape.of(POS_A);
        shapeB = Shape.of(POS_B);
        shapeAB = Shape.of(POS_A, POS_B);
        shapeBC = Shape.of(POS_B, POS_C);
        shape1 = Shape.of(POS_1);
        shape12 = Shape.of(POS_1, POS_2);
        shapeA1B1A2B2 = Shape.of(POS_A1, POS_A2, POS_B1, POS_B2);
        shapeA01 = Shape.of(POS_A01);
    }

    @Test
    public void abCoversA() {
        assertTrue(shapeAB.covers(shapeA));
    }

    @Test
    public void abDoesNotCoverBc() {
        assertFalse(shapeAB.covers(shapeBC));
    }

    @Test
    public void abCoversB() {
        assertTrue(shapeAB.covers(shapeB));
    }

    @Test
    public void bDoesNotCoverAB() {
        assertFalse(shapeB.covers(shapeAB));
    }

    @Test
    public void shapesIsAUtilityClass() {
        assertUtilityClass(Shapes.class);
    }

    @Test
    public void emptyShapeIterable() {
        assertEmptyShape(Shape.of(Collections.<Position> emptySet()));
    }

    @Test
    public void constructEmptyShapeUsingVarargs() {
        assertEmptyShape(Shape.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void intersectionOfIncompatibleShapes() {
        intersection(shapeA, shape1);
    }

    @Test
    public void intersectionOfCompatibleShapesWithEmptyResult() {
        assertEmptyShape(intersection(shapeA, shapeB));
    }

    @Test
    public void intersectionIsEqualToFreshShape() {
        assertEquals(shapeB, intersectionAbBc());
    }

    @Test
    public void intersectionHasCorrectPositionSet() {
        assertEquals(ImmutableSet.of(POS_B), intersectionAbBc().positionSet());
    }

    @Test
    public void intersectionHasCorrectSize() {
        assertEquals(1, intersectionAbBc().size());
    }

    @Test
    public void intersectionHasCorrectDimensionality() {
        assertEquals(1, intersectionAbBc().dimensionality());
    }

    @Test
    public void intersectionHasCorrectDimensions() {
        assertEquals(ImmutableSet.of(String.class), intersectionAbBc().dimensionSet());
    }

    @Test(expected = NullPointerException.class)
    public void stripDimensionsWithNullShape() {
        dimensionStripped(null, Collections.<Class<?>> emptySet());
    }

    @Test(expected = NullPointerException.class)
    public void stripDimensionsWithNullSet() {
        dimensionStripped(Shape.empty(), null);
    }

    @Test
    public void stripDimensionsOfEmptyShape() {
        Shape result = dimensionStripped(Shape.empty(), Collections.<Class<?>> emptySet());
        assertEquals(Shape.empty(), result);
    }

    @Test
    public void stripNonExistingDimensionsFromShape() {
        Shape result = dimensionStripped(shapeAB, ImmutableSet.of(Integer.class));
        assertEquals(shapeAB, result);
    }

    @Test
    public void dimensionStrippedIsEqualToConstructed() {
        Shape result = dimensionStripped(shapeA1B1A2B2, ImmutableSet.of(Integer.class));
        assertEquals(Shape.of(POS_A, POS_B), result);
    }

    @Test
    public void dimensionStrippedHasCorrectDimensionality() {
        Shape result = dimensionStripped(shapeA1B1A2B2, ImmutableSet.of(Integer.class));
        assertEquals(1, result.dimensionality());
    }

    @Test
    public void dimensionStrippedHasCorrectDimensions() {
        Shape result = dimensionStripped(shapeA1B1A2B2, ImmutableSet.of(Integer.class));
        assertEquals(ImmutableSet.of(String.class), result.dimensionSet());
    }

    @Test
    public void dimensionStrippedHasCorrectSize() {
        Shape result = dimensionStripped(shapeA1B1A2B2, ImmutableSet.of(Integer.class));
        assertEquals(2, result.size());
    }

    @Test
    public void shapeHasInitiallyCorrectDimensionality() {
        assertEquals(2, shapeA1B1A2B2.dimensionality());
    }

    @Test
    public void shapeHasInitiallyCorrectSize() {
        assertEquals(4, shapeA1B1A2B2.size());
    }

    @Test
    public void dimensionalIntersectionOfEmpyShapesIsEmptySet() {
        Set<Class<?>> intersection = dimensionalIntersection(Shape.empty(), Shape.empty());
        assertEquals(Collections.emptySet(), intersection);
    }

    @Test
    public void dimensionalIntersectionOfDifferentShapesGivesCorrectDimensions() {
        Set<Class<?>> intersection = dimensionalIntersection(shapeA1B1A2B2, shapeA01);
        assertEquals(ImmutableSet.of(String.class), intersection);
    }

    @Test
    public void dimensionalIntersectionOfSameShapeGivesAllDimensions() {
        Set<Class<?>> intersection = dimensionalIntersection(shapeA1B1A2B2, shapeA1B1A2B2);
        assertEquals(ImmutableSet.of(String.class, Integer.class), intersection);
    }

    @Test(expected = NullPointerException.class)
    public void outerProductLeftNullThrows() {
        outerProduct(null, Shape.empty());
    }

    @Test(expected = NullPointerException.class)
    public void outerProductRightNullThrows() {
        outerProduct(Shape.empty(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void outerProductWithDimensionOverlapThrows() {
        outerProduct(shapeA01, shapeA1B1A2B2);
    }

    @Test
    public void outerProductWithNonOverlappingDimensionsGivesCorrectResult() {
        Shape result = outerProduct(shapeAB, shape12);
        assertEquals(shapeA1B1A2B2, result);
    }

    private Shape intersectionAbBc() {
        return intersection(shapeAB, shapeBC);
    }

    private static void assertEmptyShape(Shape shape) {
        assertEquals(0, shape.size());
        assertEquals(0, shape.dimensionality());
        assertEquals(emptySet(), shape.positionSet());
        assertEquals(emptySet(), shape.dimensionSet());
    }

}
