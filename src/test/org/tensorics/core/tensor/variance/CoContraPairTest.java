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

package org.tensorics.core.tensor.variance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CoContraPairTest {

    @Test
    public void leftNullThrows() {
        assertThrows(NullPointerException.class, () -> CoContraDimensionPair.ofLeftRight(null, Plane.class));
    }

    @Test
    public void rightNullThrows() {
        assertThrows(NullPointerException.class, () -> CoContraDimensionPair.ofLeftRight(Plane.class, null));
    }

    @Test
    public void bothSameThrow() {
        assertThrows(IllegalArgumentException.class, () -> CoContraDimensionPair.ofLeftRight(Bpm.class, Bpm.class));
    }

    @Test
    public void bothContravariantThrow() {
        assertThrows(IllegalArgumentException.class, () -> CoContraDimensionPair.ofLeftRight(Bpm.class, Plane.class));
    }

    @Test
    public void bothCovariantThrow() {
        assertThrows(IllegalArgumentException.class, () -> CoContraDimensionPair.ofLeftRight(CoBpm.class, CoPlane.class));
    }

    @Test
    public void notMathingContraCoPairThrows() {
        assertThrows(IllegalArgumentException.class, () -> CoContraDimensionPair.ofLeftRight(Bpm.class, CoPlane.class));
    }

    @Test
    public void notMathingCoContraPairThrows() {
        assertThrows(IllegalArgumentException.class, () -> CoContraDimensionPair.ofLeftRight(CoBpm.class, Plane.class));
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
