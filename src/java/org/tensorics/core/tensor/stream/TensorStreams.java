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

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Utility class for producing streams of Entry<Position, T> out of Tensors and collecting them back into Tensors.
 * 
 * @author mihostet
 */
public final class TensorStreams {
    private TensorStreams() {
        /* static only */
    }

    /**
     * Return a stream of all entries of the tensor
     * 
     * @param tensor
     * @return
     */
    public static <S> Stream<Map.Entry<Position, S>> tensorEntryStream(Tensor<S> tensor) {
        return tensor.asMap().entrySet().stream();

    }

    /**
     * Build a collector to collect a stream of Entry<Position,T> to a generic Tensor<T>
     * 
     * @return
     */
    public static <T> TensorCollector<Map.Entry<Position, T>, T> toTensor() {
        return new TensorCollector<>(Map.Entry::getKey, Map.Entry::getValue);
    }

    /**
     * Build a collector to collect an arbitrary stream to a generic Tensor<T>. Functions mapping the values to
     * {@link Position} and T must be provided.
     * 
     * @param positionMapper function mapping stream values to Position
     * @param valueMapper function mapping stream values to tensor values
     * @return
     */
    public static <V, T> TensorCollector<V, T> toTensor(Function<V, Position> positionMapper,
            Function<V, T> valueMapper) {
        return new TensorCollector<>(positionMapper, valueMapper);
    }

    /**
     * Build a collector to collect a stream of Entry<Position,T> to a Tensorbacked<T> class. The {@link Position}s in
     * the stream must be consistent with the dimensions of the Tensorbacked.
     * 
     * @param tensorBackedClass the tensorbacked to produce
     * @return
     */
    public static <T, TB extends Tensorbacked<T>> TensorbackedCollector<Map.Entry<Position, T>, T, TB> toTensorbacked(
            Class<TB> tensorBackedClass) {
        return new TensorbackedCollector<>(tensorBackedClass, Map.Entry::getKey, Map.Entry::getValue);
    }

    /**
     * Build a collector to collect an arbitrary stream to a Tensorbackedclass. Functions mapping the values to
     * {@link Position} and T must be provided. The {@link Position}s generated must be consistent with the dimensions
     * of the Tensorbacked.
     * 
     * @param tensorBackedClass the tensorbacked to produce
     * @param positionMapper function mapping stream values to Position
     * @param valueMapper function mapping stream values to tensor values
     * @return
     */
    public static <V, T, TB extends Tensorbacked<T>> TensorbackedCollector<V, T, TB> toTensorbacked(
            Class<TB> tensorBackedClass, Function<V, Position> positionMapper, Function<V, T> valueMapper) {
        return new TensorbackedCollector<>(tensorBackedClass, positionMapper, valueMapper);
    }

}
