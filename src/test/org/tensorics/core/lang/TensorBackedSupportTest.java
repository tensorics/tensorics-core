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

package org.tensorics.core.lang;

import static javax.measure.unit.SI.METER;
import static javax.measure.unit.SI.MICRO;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.QuantityTensorSupport;
import org.tensorics.core.tensor.lang.QuantityTensors;
import org.tensorics.core.tensor.lang.TensorSupport;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.orbit.MultibeamOrbit;
import org.tensorics.core.tensorbacked.orbit.MultibeamQOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.OrbitCoordinate;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.units.JScienceUnit;

import com.google.common.base.Optional;


/**
 * Test that verifies if {@link TensorSupport} is valid for {@link Tensorbacked} objects as well. Analogous tests covers
 * the and {@link QuantityTensorSupport}
 * 
 * @author agorzaws
 */
public class TensorBackedSupportTest {

    /* TensorBacked<Double> */
    private MultibeamOrbit multibeamTensorBacked1;
    /* TensorBacked<QuantifiedValue<Double>> */
    private MultibeamQOrbit multibeamTensorBackedOfQV;
    private MultibeamQOrbit multibeamTensorBackedOfQV2;
    private MultibeamQOrbit multibeamTensorBackedofQVWithErrors;
    private TensoricSupport<Double> fullTensoricSupport;

    @Before
    public void setUp() throws Exception {
        fullTensoricSupport = Tensorics.using(Structures.doubles());
        multibeamTensorBacked1 = new MultibeamOrbit(prepareDoubleTensor(1.0));
        multibeamTensorBackedOfQV = new MultibeamQOrbit(prepareTensorOfQuantifiedValues(1.980));
        multibeamTensorBackedOfQV2 = new MultibeamQOrbit(prepareQuantifiedTensor(1.1, 0.0, true));
        multibeamTensorBackedofQVWithErrors = new MultibeamQOrbit(prepareQuantifiedTensor(1.1, 0.1, false));
    }

    /* TensorBacked<Double> */
    @Test
    public void testTensorOfDoubleSimple() {
        MultibeamOrbit negativeOf = fullTensoricSupport.negativeOf(multibeamTensorBacked1);
        double value = negativeOf.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(-1.0, value, 0.001);
        double value2 = negativeOf.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(-9.0, value2, 0.001);
    }

    @Test
    public void testTensorOfDoubleCalc() {
        MultibeamOrbit negativeOf = fullTensoricSupport.negativeOf(multibeamTensorBacked1);
        MultibeamOrbit plus = fullTensoricSupport.calculate(multibeamTensorBacked1).plus(negativeOf);
        double value = plus.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(0.0, value, 0.001);
        double value2 = plus.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(0.0, value2, 0.001);
    }

    @Test
    public void testTensorOfDoubleCalcTimesValue() {
        MultibeamOrbit elementTimesBy = fullTensoricSupport.calculate(multibeamTensorBacked1).elementTimesV(2.2);
        double value3 = elementTimesBy.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(19.8, value3, 0.001);
    }

    @Test
    public void testTensorOfDoubleCalcDividedValue() {
        MultibeamOrbit elementTimesBy = fullTensoricSupport.calculate(multibeamTensorBacked1).elementDividedByV(2.0);
        double value3 = elementTimesBy.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(4.5, value3, 0.001);
    }

    @Test
    public void testTensorOfDoubleCalcTimesTensor() {
        double value1 = multibeamTensorBacked1.getValueAt("name 9", Beam.B1, Plane.H);
        MultibeamOrbit negativeOf = fullTensoricSupport.negativeOf(multibeamTensorBacked1);
        MultibeamOrbit elementTimesBy = fullTensoricSupport.calculate(multibeamTensorBacked1).elementTimes(negativeOf);
        double value3 = elementTimesBy.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(-1 * value1 * value1, value3, 0.001);
    }

    @Test
    public void testTensorOfDoubleCalcDividedTensor() {
        MultibeamOrbit negativeOf = fullTensoricSupport.negativeOf(multibeamTensorBacked1);
        MultibeamOrbit elementTimesBy = fullTensoricSupport.calculate(multibeamTensorBacked1).elementDividedBy(
                negativeOf);
        double value3 = elementTimesBy.getValueAt("name 9", Beam.B1, Plane.H);
        assertEquals(-1, value3, 0.001);
    }

    /* TensorBacked<QuantifiedValue<Double>> */

    @Test
    public void testTensorOfQVDouble() {
        double value2 = multibeamTensorBackedOfQV2.getValueAt("name 9", Beam.B1, Plane.H).value();
        assertEquals(3, multibeamTensorBackedOfQV2.tensor().shape().dimensionality());
        assertEquals(9.9, value2, 0.001);
        Tensor<Double> values = QuantityTensors.valuesOf(multibeamTensorBackedOfQV2.tensor());
        assertEquals(3, values.shape().dimensionality());
        assertEquals(multibeamTensorBackedOfQV2.tensor().shape(), values.shape());
    }

    @Test
    public void testTensorOfQVDoubleCalc() {
        QuantifiedValue<Double> valueAt = multibeamTensorBackedOfQV.getValueAt("name 1", Beam.B2, Plane.H);
        MultibeamQOrbit negativeOf = fullTensoricSupport.negativeOfQ(multibeamTensorBackedOfQV);
        QuantifiedValue<Double> valueAtNegative = negativeOf.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(valueAt.value() * -1, valueAtNegative.value(), 0.001);
    }

    @Test
    public void testTensorOfQVDoubleCalcTimesValue() {
        QuantifiedValue<Double> valueAt = multibeamTensorBackedOfQV.getValueAt("name 1", Beam.B2, Plane.H);
        MultibeamQOrbit result = fullTensoricSupport.calculateQ(multibeamTensorBackedOfQV).elementTimesV(valueAt);
        QuantifiedValue<Double> resultValueAt = result.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(valueAt.value() * valueAt.value(), resultValueAt.value(), 0.001);
    }

    @Test
    public void testTensorOfQVDoubleCalcDividedValue() {
        QuantifiedValue<Double> valueAt = multibeamTensorBackedOfQV.getValueAt("name 1", Beam.B2, Plane.H);
        MultibeamQOrbit result = fullTensoricSupport.calculateQ(multibeamTensorBackedOfQV).elementDividedByV(valueAt);
        QuantifiedValue<Double> resultValueAt = result.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(valueAt.value() / valueAt.value(), resultValueAt.value(), 0.001);
    }

    @Test
    public void testTensorOfQVDoubleCalcTimesTensor() {
        QuantifiedValue<Double> valueAt = multibeamTensorBackedOfQV.getValueAt("name 1", Beam.B2, Plane.H);
        MultibeamQOrbit negativeOf = fullTensoricSupport.negativeOfQ(multibeamTensorBackedOfQV);
        MultibeamQOrbit result = fullTensoricSupport.calculateQ(multibeamTensorBackedOfQV).elementTimes(negativeOf);
        QuantifiedValue<Double> resultValueAt = result.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(-1 * fullTensoricSupport.calculate(valueAt).times(valueAt).value(), resultValueAt.value(), 0.001);
    }

    @Test
    public void testTensorOfQVDoubleCalcDividedTensor() {
        MultibeamQOrbit negativeOf = fullTensoricSupport.negativeOfQ(multibeamTensorBackedOfQV);
        MultibeamQOrbit result = fullTensoricSupport.calculateQ(multibeamTensorBackedOfQV).elementDividedBy(negativeOf);
        QuantifiedValue<Double> resultValueAt = result.getValueAt("name 1", Beam.B2, Plane.H);
        assertEquals(-1, resultValueAt.value(), 0.001);
    }

    @Test
    public void testTensorOfQVDoubleWithErrors() {
        QuantifiedValue<Double> value = multibeamTensorBackedofQVWithErrors.getValueAt("name 8", Beam.B2, Plane.V);
        fullTensoricSupport.calculateQ(multibeamTensorBackedofQVWithErrors).plus(multibeamTensorBackedOfQV2);
        assertEquals(3, multibeamTensorBackedofQVWithErrors.tensor().shape().dimensionality());
        assertEquals(8.8, value.value(), 0.001);
        assertEquals(true, value.error().isPresent());
        assertEquals(0.1, value.error().get().doubleValue(), 0.01);
        assertEquals(false, value.validity());
    }

    @Test
    public void testQTensorOfDouble() {
        double value2 = multibeamTensorBackedOfQV2.getValueAt("name 9", Beam.B1, Plane.H).value();
        assertEquals(3, multibeamTensorBackedOfQV2.tensor().shape().dimensionality());
        assertEquals(9.9, value2, 0.001);
        Tensor<Double> values = QuantityTensors.valuesOf(multibeamTensorBackedOfQV2.tensor());
        assertEquals(3, values.shape().dimensionality());
        assertEquals(multibeamTensorBackedOfQV2.tensor().shape(), values.shape());
    }

    private Tensor<Double> prepareDoubleTensor(double factor) {
        Builder<Double> builder = ImmutableTensor.builder(getDimensions());
        for (int i = 0; i < 10; i++) {
            builder.at(createCoordinates(i)).put(factor * i);
        }
        return builder.build();
    }

    private Tensor<QuantifiedValue<Double>> prepareTensorOfQuantifiedValues(double factor) {
        Builder<QuantifiedValue<Double>> builder = ImmutableTensor.builder(getDimensions());
        for (int i = 0; i < 10; i++) {
            builder.at(createCoordinates(i)).put(Tensorics.quantityOf(factor * i, JScienceUnit.of(MICRO(METER))));
        }
        return builder.build();
    }

    private Tensor<QuantifiedValue<Double>> prepareQuantifiedTensor(double factor, double error, boolean valid) {
        Builder<QuantifiedValue<Double>> builder = ImmutableTensor.builder(getDimensions());
        for (int i = 0; i < 10; i++) {

            QuantifiedValue<Double> entryValue = Tensorics.quantityOf(factor * i, JScienceUnit.of(MICRO(METER)));
            if (!valid && i % 2 == 0) {
                Optional<Double> newError = Optional.of(error);
                entryValue = Tensorics.quantityOf(factor * i, JScienceUnit.of(MICRO(METER))).withError(newError)
                        .withValidity(false);
            }
            builder.at(createCoordinates(i)).put(entryValue);
        }
        return builder.build();
    }

    private Set<OrbitCoordinate> createCoordinates(int i) {
        Set<OrbitCoordinate> coor = new HashSet<>();
        coor.add(i % 2 == 0 ? Plane.V : Plane.H);
        coor.add(i % 3 == 0 ? Beam.B1 : Beam.B2);
        coor.add(new Bpm("name " + i));
        return coor;
    }

    private Set<Class<?>> getDimensions() {
        Set<Class<?>> dimensions = new HashSet<>();
        dimensions.add(Bpm.class);
        dimensions.add(Beam.class);
        dimensions.add(Plane.class);
        return dimensions;
    }
}
