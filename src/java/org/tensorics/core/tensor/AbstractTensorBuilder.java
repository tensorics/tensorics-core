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

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tensorics.core.tensor.operations.TensorInternals;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * @author kfuchsbe
 * @param <V> the type of the elements of the tensor to build
 */
public abstract class AbstractTensorBuilder<V> implements TensorBuilder<V> {

    private final Set<Class<?>> dimensions;
    private final VerificationCallback<V> callback;
    private Position context = Position.empty();

    public AbstractTensorBuilder(Set<Class<?>> dimensions, VerificationCallback<V> callback) {
        Preconditions.checkArgument(dimensions != null, "Argument '" + "dimensions" + "' must not be null!");
        Preconditions.checkArgument(callback != null, "Argument '" + "callback" + "' must not be null!");
        Coordinates.checkClassesRelations(dimensions);
        this.dimensions = ImmutableSet.copyOf(dimensions);
        this.callback = callback;
    }

    public AbstractTensorBuilder(Set<Class<?>> dimensions) {
        this(dimensions, new VerificationCallback<V>() {

            @Override
            public void verify(V scalar) {
                /* Nothing to do */
            }
        });
    }

    @Override
    public final TensorBuilder<V> put(Position position, V value) {
        Preconditions.checkNotNull(value, "value must not be null!");
        Preconditions.checkNotNull(position, "position must not be null");
        Positions.assertConsistentDimensions(position, this.dimensions);
        this.callback.verify(value);
        this.putIt(position, value);
        return this;
    }

    protected abstract void putIt(Position position, V value);

    @Override
    public TensorBuilder<V> context(Position newContext) {
        Preconditions.checkNotNull(newContext, "context must not be null");
        checkIfContextValid(newContext);
        this.context = newContext;
        return this;
    }

    private void checkIfContextValid(Position context2) {
        for (Class<?> oneDimensionClass : context2.dimensionSet()) {
            if (dimensions.contains(oneDimensionClass)) {
                throw new IllegalStateException("Inconsistent state: " + oneDimensionClass
                        + " you are trying to put in to context is a BASE dimension of the tensor!");
            }
        }
    }

    @Override
    public TensorBuilder<V> putAll(Tensor<V> tensor) {
        this.putAll(Position.empty(), tensor);
        return this;
    }

    @Override
    public final TensorBuilder<V> putAll(Position position, Tensor<V> tensor) {
        checkNotNull(tensor, "The tensor must not be null!");
        putAll(position, TensorInternals.mapFrom(tensor));
        return this;
    }

    @Override
    public TensorBuilder<V> putAll(Map<Position, V> newEntries) {
        putAll(Position.empty(), newEntries);
        return this;
    }

    @Override
    public TensorBuilder<V> putAll(Position position, Map<Position, V> map) {
        checkNotNull(map, "The map must not be null!");
        checkNotNull(position, "The position must not be null!");
        for (Entry<Position, V> entry : map.entrySet()) {
            put(Positions.union(position, entry.getKey()), entry.getValue());
        }
        return this;
    }

    @Override
    public TensorBuilder<V> put(java.util.Map.Entry<Position, V> entry) {
        this.put(entry.getKey(), entry.getValue());
        return this;
    }

    public Set<Class<?>> dimensions() {
        return dimensions;
    }

    public Position context() {
        return this.context;
    }
}