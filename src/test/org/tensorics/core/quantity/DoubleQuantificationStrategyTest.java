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

package org.tensorics.core.quantity;

import static javax.measure.unit.SI.AMPERE;
import static javax.measure.unit.SI.MICRO;
import static javax.measure.unit.SI.MILLI;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.fields.doubles.DoubleCheating;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.options.JScienceQuantificationStrategy;
import org.tensorics.core.quantity.options.OperandPair;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

public class DoubleQuantificationStrategyTest {

    private QuantificationStrategy<Double> strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new JScienceQuantificationStrategy<>(new DoubleCheating());
    }

    @Test
    public void testSameUnits() {
        QuantifiedValue<Double> scalar1 = Tensorics.quantityOf(12.5, JScienceUnit.of(MILLI(AMPERE)));
        QuantifiedValue<Double> scalar2 = Tensorics.quantityOf(7.5, JScienceUnit.of(MILLI(AMPERE)));
        OperandPair<Double, Unit> pair = strategy.asSameUnit(scalar1, scalar2);
        assertEquals(JScienceUnit.of(MILLI(AMPERE)), pair.unit());
        assertEquals(12.5, pair.left().value(), 0.00001);
        assertEquals(7.5, pair.right().value(), 0.00001);
    }

    @Test
    public void testDifferentUnits() throws Exception {
        QuantifiedValue<Double> scalar1 = Tensorics.quantityOf(12.5, JScienceUnit.of(MILLI(AMPERE)));
        QuantifiedValue<Double> scalar2 = Tensorics.quantityOf(7.5, JScienceUnit.of(MICRO(AMPERE)));
        OperandPair<Double, Unit> pair = strategy.asSameUnit(scalar1, scalar2);
        assertEquals(JScienceUnit.of(AMPERE), pair.unit());
        assertEquals(0.0125, pair.left().value(), 0.000000001);
        assertEquals(0.0000075, pair.right().value(), 0.000000001);
    }

}
