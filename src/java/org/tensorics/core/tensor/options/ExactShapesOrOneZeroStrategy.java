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

package org.tensorics.core.tensor.options;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * Implementation of {@link ShapingStrategy} that checks if for two given {@link Tensor} objects shapes are exactly the
 * same (type of the coordinates, number of dimensions and coordinates instances). Otherwise it throws and
 * IllegalArgumentException.
 * 
 * @author agorzaws
 */
public class ExactShapesOrOneZeroStrategy implements ShapingStrategy {

    @Override
    public <C> Shape shapeLeftRight(Tensor<?> first, Tensor<?> second) {
        Shape shapeOfFirst = first.shape();
        Shape shapeOfSecond = second.shape();
        if (shapeOfFirst.equals(shapeOfSecond)) {
            return shapeOfFirst;
        }

        if (second.shape().dimensionality() == 0 && first.shape().dimensionality() > 0) {
            return first.shape();
        }

        if (first.shape().dimensionality() == 0 && second.shape().dimensionality() > 0) {
            return second.shape();
        }

        throw new IllegalArgumentException("Given tensors have different SHAPES. Cannot perform " + this.getClass()
                + " multiplication with them.");
    }

    @Override
    public Class<ShapingStrategy> getMarkerInterface() {
        return ShapingStrategy.class;
    }
}
