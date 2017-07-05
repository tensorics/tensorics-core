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

package org.tensorics.core.function;

import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Contains utility methods for functions related to tensorics
 * 
 * @author kfuchsbe
 */
public final class TensoricFunctions {

    private TensoricFunctions() {
        /* only static methods */
    }

    public final static <S> Function<Position, S> forTensor(Tensor<S> tensor) {
        return new PositionToScalarFunction<S>(tensor);
    }

    public final static <S> Function<Position, S> forTensorbacked(Tensorbacked<S> tensor) {
        return forTensor(tensor.tensor());
    }

    public final static <C> Function<C, Position> singleCoordinate() {
        return new CoordinateToPositionFunction<C>();
    }

    public final static <C, S> Function<C, S> singleCoordinateToValue(Tensor<S> tensor) {
        return forTensor(tensor).compose(TensoricFunctions.<C> singleCoordinate());
    }

    public final static <C, S> Function<C, S> singleCoordinateToValue(Tensorbacked<S> tensorbacked) {
        return singleCoordinateToValue(tensorbacked.tensor());
    }

}
