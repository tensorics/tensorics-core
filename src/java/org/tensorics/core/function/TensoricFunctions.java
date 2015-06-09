/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import static org.tensorics.core.lang.Tensorics.builderFor;
import static org.tensorics.core.lang.Tensorics.dimensionsOf;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensorbacked.Tensorbacked;

import com.google.common.base.Function;
import com.google.common.base.Functions;

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

    public final static <C, S> Function<C, S> forSingleCoordinate(Tensor<S> tensor) {
        return Functions.compose(forTensor(tensor), TensoricFunctions.<C> singleCoordinate());
    }

    public final static <C, S> Function<C, S> singleCoordinateToValue(Tensorbacked<S> tensorbacked) {
        return forSingleCoordinate(tensorbacked.tensor());
    }

    public final static <T, R> Tensor<R> transform(Tensor<T> inTensor, Function<T, R> transformation) {
        TensorBuilder<R> builder = Tensorics.builder(dimensionsOf(inTensor));
        for (Entry<T> entry : inTensor.entrySet()) {
            builder.putAt(transformation.apply(entry.getValue()), entry.getPosition());
        }
        return builder.build();
    }

}
