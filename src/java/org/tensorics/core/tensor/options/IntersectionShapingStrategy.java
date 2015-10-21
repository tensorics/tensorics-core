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

import static org.tensorics.core.tensor.Shapes.intersection;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * A strategy for shaping tensors resulting from binary (element wise) operations, which just takes the intersection of
 * both shapes. This means that the resulting shape will be such, that it will only contain positions which are
 * contained in both incoming shapes.
 * <p>
 * Example: <blockquote>
 * 
 * <pre>
 * Shape result = shapeLeftRight(Shape.of(POS_A, POS_B), Shape.of(POS_B, POS_C));
 * result.equals(Shape.of(POS_B)); // would be true
 * </pre>
 * 
 * </blockquote>
 * 
 * @author agorzaws, kfuchsbe
 */
public class IntersectionShapingStrategy implements ShapingStrategy {

    @Override
    public <C> Shape shapeLeftRight(Tensor<?> left, Tensor<?> right) {
        return intersection(left.shape(), right.shape());
    }

    @Override
    public Class<ShapingStrategy> getMarkerInterface() {
        return ShapingStrategy.class;
    }
}
