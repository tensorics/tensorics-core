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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.tensorics.core.testing.TestUtil.assertUtilityClass;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensorbacked.dimtyped.Tensorbacked1dBuilder;
import org.tensorics.core.tensorbacked.interfacebacked.SingleBeamOrbitIf;
import org.tensorics.core.tensorbacked.orbit.SinglebeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

public class TensorbackedsTest {

    @Test
    public void verifyUtilityClass() {
        assertUtilityClass(Tensorbackeds.class);
    }

    @Test
    public void interfaceBackedIsInstantiated() {
        SimpleTensorbackedBuilder<Double, SingleBeamOrbitIf> builder = Tensorbackeds.builderFor(SingleBeamOrbitIf.class);
        builder.put(Tensorics.at(new Bpm("BPM1"), Plane.H), 1.0);
        SingleBeamOrbitIf tb = builder.build();
        assertThat(tb.tensor().shape().size()).isEqualTo(1);
    }

    @Test
    public void dimtypedBuilder2dCreation() {
        SingleBeamOrbitIf t = Tensorbackeds.builderFor2D(SingleBeamOrbitIf.class)
                .put(new Bpm("bpm1"), Plane.H, 0.1)
                .build();

        assertThat(t.tensor().shape().dimensionSet()).containsExactly(Bpm.class, Plane.class);
        assertThat(t.tensor().shape().size()).isEqualTo(1);
        assertThat(t.get(new Bpm("bpm1"), Plane.H)).isCloseTo(0.1, offset(0.000001));

    }


}
