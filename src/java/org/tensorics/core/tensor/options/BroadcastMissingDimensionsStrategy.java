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

import static com.google.common.base.Preconditions.checkNotNull;
import static org.tensorics.core.tensor.Shapes.dimensionStripped;

import java.util.Set;

import org.tensorics.core.tensor.BroadcastedTensorView;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Shapes;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;

import com.google.common.collect.Sets;

/**
 * A broadcasting strategies that broadcasts all dimensions which are not available in one tensor to the shape of the
 * second tensor. For example, lets assume tensors of double values:
 * <ul>
 * <li>the left tensor has one dimension X with two entries {[x1]=1.0, [x2]=2.0},
 * <li>the right tensor has one dimension Y with two entries {[y1]=0.1, [y2]=0.2},
 * </ul>
 * then these two tensors will both be broadcasted to tensors with two dimensions (X, Y) and four values:
 * <ul>
 * <li>broadcasted left: {[x1,y1]=1.0, [x1,y2]=1.0, [x2,y1]=2.0, [x2,y2]=2.0},
 * <li>broadcasted left: {[x1,y1]=0.1, [x1,y2]=0.2, [x2,y1]=0.1, [x2,y2]=0.2}.
 * </ul>
 * This strategy is rather close to the behaviour of
 * <a href="http://docs.scipy.org/doc/numpy/user/basics.broadcasting.html">numpy</a>, but has one very important
 * difference: It does NOT broadcast dimensions with one entry. So, if e.g. in the following example, the second tensor
 * would have had a different shape, like {[x1, y1]=1.0, [x2, y1]=2.0}, then it would have remained the same after
 * broadcasting and the shapes of the two resulting tensors would be different. This still can be useful in
 * calculations. The final shape for the result is determined from the two input shapes by a different strategy, the
 * shaping strategy.
 * <p>
 * The reason for this important difference to numpy, is that we consider this as more consistent in the general case:
 * If a dimension is not present in a tensor, we treat it as 'applicable for all' while if the dimension is present with
 * one entry, then it is clearly defined where the values are positioned in this dimension and it would be dangerous to
 * assume they would be applicable everywhere.
 * 
 * @author kfuchsbe
 */
public class BroadcastMissingDimensionsStrategy implements BroadcastingStrategy {

    @Override
    public <V> TensorPair<V> broadcast(Tensor<V> left, Tensor<V> right, Set<Class<?>> excludedDimensions) {
        checkNotNull(left, "left tensor must not be null");
        checkNotNull(right, "right tensor must not be null");

        Set<Class<?>> dimensionalIntersection = Shapes.parentDimensionalIntersection(left.shape(), right.shape());
        Set<Class<?>> notBroadcastedDimensions = Sets.union(dimensionalIntersection, excludedDimensions)
                .immutableCopy();

        Shape missingLeft = dimensionStripped(right.shape(), notBroadcastedDimensions);
        Shape missingRight = dimensionStripped(left.shape(), notBroadcastedDimensions);

        return TensorPair.fromLeftRight(new BroadcastedTensorView<V>(left, missingLeft),
                new BroadcastedTensorView<V>(right, missingRight));
    }

    @Override
    public Class<BroadcastingStrategy> getMarkerInterface() {
        return BroadcastingStrategy.class;
    }

}
