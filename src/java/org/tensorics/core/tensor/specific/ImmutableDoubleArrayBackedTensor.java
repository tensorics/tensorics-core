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
package org.tensorics.core.tensor.specific;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.tensorics.core.tensor.AbstractTensorBuilder;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.ImmutableEntry;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * A specific implementation of a tensor, that contains double values. It is backed by a simple double array to minimize
 * memory usage and improve performance.
 * 
 * @author kaifox
 */
public class ImmutableDoubleArrayBackedTensor implements Tensor<Double> {

    private final PositionIndexer indexer;
    private final double[] values;
    private final Context tensorContext;

    public ImmutableDoubleArrayBackedTensor(Builder builder) {
        this.indexer = builder.indexer;
        this.values = Arrays.copyOf(builder.values, builder.values.length);
        this.tensorContext = builder.getContext();
    }

    @Override
    public Double get(Position position) {
        return values[indexer.indexFor(position)];
    }

    @Override
    public Double get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    @Override
    @Deprecated
    public Set<Tensor.Entry<Double>> entrySet() {
        ImmutableSet.Builder<Tensor.Entry<Double>> builder = ImmutableSet.builder();
        for (Position position : indexer.allPositions()) {
            builder.add(new ImmutableEntry<Double>(position, get(position)));
        }
        return builder.build();
    }

    @Override
    public Map<Position, Double> asMap() {
        ImmutableMap.Builder<Position, Double> builder = ImmutableMap.builder();
        for (Position position : indexer.allPositions()) {
            builder.put(position, get(position));
        }
        return builder.build();
    }

    @Override
    public Shape shape() {
        return Shape.viewOf(indexer.dimensions(), indexer.allPositions());
    }

    @Override
    public Context context() {
        return this.tensorContext;
    }

    public static Builder builder(PositionIndexer indexer) {
        return new Builder(indexer);
    }

    /**
     * A builder for tensor which only will contain doubles
     * 
     * @author kfuchsbe
     */
    public static class Builder extends AbstractTensorBuilder<Double> {

        private final PositionIndexer indexer;
        private final double[] values;

        Builder(PositionIndexer indexer) {
            super(indexer.dimensions());
            this.indexer = indexer;
            this.values = new double[indexer.arraySize()];
        }

        @Override
        protected void putItAt(Double value, Position position) {
            this.values[indexer.indexFor(position)] = value;
        }

        public void putUncheckedAt(double value, Position position) {
            this.values[indexer.indexFor(position)] = value;
        }

        @Override
        public ImmutableDoubleArrayBackedTensor build() {
            return new ImmutableDoubleArrayBackedTensor(this);
        }

        @Override
        public void putAllMap(Map<Position, Double> newEntries) {
            for (java.util.Map.Entry<Position, Double> one : newEntries.entrySet()) {
                putItAt(one.getValue(), one.getKey());
            }
        }

        @Override
        public void removeAt(Position position) {
            throw new UnsupportedOperationException("Cannot remove a value");
        }

        @Override
        public void put(java.util.Map.Entry<Position, Double> entry) {
            putAt(entry.getValue(), entry.getKey());
        }

        @Override
        public void putAll(Tensor<Double> tensor) {
            putAllAt(tensor);
        }
    }
}
