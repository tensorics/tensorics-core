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

package org.tensorics.core.tensorbacked;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensorbacked.orbit.SinglebeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

import com.google.common.collect.Iterables;

public class TensorbackedBuilderTest {

    private static final double TESTVALUE_1_0 = 1.0;
    private static final Bpm BPM_A = new Bpm("A");
    private static final Position POS_A_H = Position.of(BPM_A, Plane.H);

    @Test
    public void emptyTensorbackedHasCorrectDimensionality() {
        assertThat(Tensorics.dimensionalityOf(emptyOrbit()), equalTo(2));
    }

    @Test
    public void emptyTensorHasSizeZero() {
        assertThat(Tensorics.sizeOf(emptyOrbit()), equalTo(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void putInsufficientCoordinateThrows() {
        Object[] coordinates = { BPM_A };
        newBuilder().put(Position.of(coordinates), 1.0);
    }

    @Test
    public void putOneCorrectValueWorks() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        Object[] coordinates = { BPM_A, Plane.H };
        builder.put(Position.of(coordinates), 1.0);
        SinglebeamOrbit orbit = builder.build();
        assertThat(Tensorics.sizeOf(orbit), equalTo(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void putAtOnePositionWithWrongCoordinatesThrows() {
        newBuilder().put(Position.of(BPM_A), TESTVALUE_1_0);
    }

    @Test
    public void putOneAtPositionHasCorrectSize() {
        assertThat(Tensorics.sizeOf(oneValuePosAH()), equalTo(1));
    }

    @Test
    public void putOneAtPositionHasCorrectValue() {
        assertThat(oneValuePosAH().tensor().get(POS_A_H), equalTo(TESTVALUE_1_0));
    }

    @Test
    public void putOneValueByCoordinatesHasCorrectValue() {
        assertThat(oneValueCoordinatesAH().tensor().get(POS_A_H), equalTo(TESTVALUE_1_0));
    }

    @Test
    public void oneValueCoordinatesEqualsOneValuePos() {
        assertThat(oneValueCoordinatesAH(), equalTo(oneValuePosAH()));
    }

    @Test(expected = NullPointerException.class)
    public void putNullEntryThrows() {
        java.util.Map.Entry<Position, Double> toPut = null;
        newBuilder().put(toPut);
    }

    @Test
    public void putOneEntryIsEqualToOriginal() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        builder.put(Iterables.getFirst(Tensorics.mapFrom(oneValuePosAH().tensor()).entrySet(), null));
        assertThat(builder.build(), equalTo(oneValuePosAH()));
    }

    @Test(expected = NullPointerException.class)
    public void putNullEntriesThrows() {
        Set<java.util.Map.Entry<Position, Double>> toPut = null;
        newBuilder().putAll(toPut);
    }

    @Test
    public void putEmptyCollectionResultsInEmptyTensor() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        builder.putAll(Collections.<java.util.Map.Entry<Position, Double>> emptySet());
        assertThat(Tensorics.sizeOf(builder.build()), equalTo(0));
    }

    @Test
    public void copyByEntryEqualsToOriginal() {
        assertThat(oneValuePosCopied(), equalTo(oneValuePosAH()));
    }

    @Test
    public void copyByEntryEqualsHasCorrectSize() {
        assertThat(Tensorics.sizeOf(oneValuePosCopied()), equalTo(1));
    }

    private SinglebeamOrbit oneValuePosCopied() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        builder.putAll(Tensorics.mapFrom(oneValuePosAH().tensor()).entrySet());
        SinglebeamOrbit copiedOrbit = builder.build();
        return copiedOrbit;
    }

    private SinglebeamOrbit oneValueCoordinatesAH() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        builder.put(Position.of(POS_A_H.coordinates()), TESTVALUE_1_0);
        return builder.build();
    }

    private SinglebeamOrbit oneValuePosAH() {
        TensorbackedBuilder<Double, SinglebeamOrbit> builder = newBuilder();
        builder.put(POS_A_H, TESTVALUE_1_0);
        return builder.build();
    }

    private TensorbackedBuilder<Double, SinglebeamOrbit> newBuilder() {
        return Tensorics.builderFor(SinglebeamOrbit.class);
    }

    private SinglebeamOrbit emptyOrbit() {
        return newBuilder().build();
    }

}
