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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.measure.unit.SI;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.TensoricDoubles;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.units.JScienceUnit;

public class ImmutableQuantifiedScalarTest {

    private ImmutableQuantifiedValue<Double> scalar;
    private ImmutableQuantifiedValue<Double> zero;
    private ImmutableQuantifiedValue<Double> negativeScalar;

    @Before
    public void setUp() {
        zero = Tensorics.quantityOf(0.0, JScienceUnit.of(SI.AMPERE));
        scalar = Tensorics.quantityOf(10.5, JScienceUnit.of(SI.AMPERE));
        negativeScalar = Tensorics.quantityOf(-42.5, JScienceUnit.of(SI.AMPERE));
    }

    @Test
    public void testUnit() {
        assertEquals(JScienceUnit.of(SI.AMPERE), scalar.unit());
    }

    @Test
    public void testValue() {
        assertEquals(10.5, scalar.value(), 0.000001);
    }

    @Test
    public void testAbsolute() {
        assertEquals(zero, TensoricDoubles.absoluteValueOf(zero));
        assertEquals(0.0, TensoricDoubles.absoluteValueOf(zero).value(), 0.000001);
        assertEquals(10.5, TensoricDoubles.absoluteValueOf(scalar).value(), 0.000001);
        assertEquals(42.5, TensoricDoubles.absoluteValueOf(negativeScalar).value(), 0.000001);
    }

    @Test
    public void serializableAndDeserializedIsEquals() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        (new ObjectOutputStream(buffer)).writeObject(scalar);
        Object deserialized = (new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))).readObject();
        assertEquals(scalar, deserialized);
    }

}
