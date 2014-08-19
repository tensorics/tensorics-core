/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.variance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CoContraPairTest {

    @Test(expected = NullPointerException.class)
    public void leftNullThrows() {
        CoContraDimensionPair.ofLeftRight(null, Plane.class);
    }

    @Test(expected = NullPointerException.class)
    public void rightNullThrows() {
        CoContraDimensionPair.ofLeftRight(Plane.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bothSameThrow() {
        CoContraDimensionPair.ofLeftRight(Bpm.class, Bpm.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bothContravariantThrow() {
        CoContraDimensionPair.ofLeftRight(Bpm.class, Plane.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bothCovariantThrow() {
        CoContraDimensionPair.ofLeftRight(CoBpm.class, CoPlane.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notMathingContraCoPairThrows() {
        CoContraDimensionPair.ofLeftRight(Bpm.class, CoPlane.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notMathingCoContraPairThrows() {
        CoContraDimensionPair.ofLeftRight(CoBpm.class, Plane.class);
    }

    @Test
    public void leftValuesIsCorrectlyReturned() {
        assertEquals(Bpm.class, bpmContraCo().left());
    }

    @Test
    public void rightValueIsCorrectlyReturned() {
        assertEquals(CoBpm.class, bpmContraCo().right());
    }

    @Test
    public void contraValueIsCorrectlyReturnedFromLeft() {
        assertEquals(Bpm.class, bpmContraCo().contravariant());
    }

    @Test
    public void coValueIsCorrectlyReturnedFromRight() {
        assertEquals(CoBpm.class, bpmContraCo().covariant());
    }

    @Test
    public void contraValueIsCorrectlyReturnedFromRight() {
        assertEquals(Plane.class, planeCoContra().contravariant());
    }

    @Test
    public void coValueIsCorrectlyReturnedFromLeft() {
        assertEquals(CoPlane.class, planeCoContra().covariant());
    }

    private CoContraDimensionPair planeCoContra() {
        return CoContraDimensionPair.ofLeftRight(CoPlane.class, Plane.class);
    }

    private CoContraDimensionPair bpmContraCo() {
        return CoContraDimensionPair.ofLeftRight(Bpm.class, CoBpm.class);
    }

    private static class Plane {
        /* Only for testing */
    }

    private static class CoPlane extends Covariant<Plane> {

        protected CoPlane(Plane coordinate) {
            super(coordinate);
        }

    }

    private static class Bpm {
        /* for testing only */
    }

    private static class CoBpm extends Covariant<Bpm> {

        protected CoBpm(Bpm coordinate) {
            super(coordinate);
        }

    }

}
