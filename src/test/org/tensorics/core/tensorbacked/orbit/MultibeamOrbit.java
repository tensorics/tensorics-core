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

package org.tensorics.core.tensorbacked.orbit;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.orbit.coordinates.Beam;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

/**
 * Test case. Multibeam class supported with TensorBacked(Double)
 * 
 * @author agorzaws
 */
@Dimensions({ Beam.class, Plane.class, Bpm.class })
public class MultibeamOrbit extends AbstractOrbit {

    private static final long serialVersionUID = 1L;

    public MultibeamOrbit(Tensor<Double> tensor) {
        super(tensor);
    }

    public double getValueAt(String string, Beam b2, Plane h) {
        return backingTensor.get(b2, h, new Bpm(string));
    }

}
