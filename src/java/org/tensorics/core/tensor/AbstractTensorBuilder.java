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

import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * @author kfuchsbe
 * @param <E> the type of the elements of the tensor to build
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class AbstractTensorBuilder<E> implements TensorBuilder<E> {

    private final Set<? extends Class<?>> dimensions;
    private final VerificationCallback<E> callback;
    private Context context = Context.empty();

    public AbstractTensorBuilder(Set<? extends Class<?>> dimensions, VerificationCallback<E> callback) {
        Preconditions.checkArgument(dimensions != null, "Argument '" + "dimensions" + "' must not be null!");
        Preconditions.checkArgument(callback != null, "Argument '" + "callback" + "' must not be null!");
        Coordinates.checkClassesRelations(dimensions);
        this.dimensions = ImmutableSet.copyOf(dimensions);
        this.callback = callback;
    }

    public AbstractTensorBuilder(Set<? extends Class<?>> dimensions) {
        this(dimensions, new VerificationCallback<E>() {

            @Override
            public void verify(E scalar) {
                /* Nothing to do */
            }
        });
    }

    /**
     * Prepares to set a value at given position (a combined set of coordinates)
     * 
     * @param entryPosition on which future value will be placed.
     * @return builder object to be able to put Value in.
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<E> at(Position entryPosition) {
        return new OngoingPut<E>(entryPosition, this);
    }

    @Override
    public final void putAt(E value, Position position) {
        Preconditions.checkNotNull(value, "value must not be null!");
        Preconditions.checkNotNull(position, "position must not be null");
        Positions.assertConsistentDimensions(position, getDimensions());
        this.callback.verify(value);
        this.putItAt(value, position);
    }

    protected abstract void putItAt(E value, Position position);

    @Override
    public final void putAt(E value, Object... coordinates) {
        this.putAt(value, Position.of(coordinates));
    }

    @Override
    public void putAt(E value, Set<?> coordinates) {
        putAt(value, Position.of(coordinates));
    }

    @Override
    public void putAllAt(Tensor<E> tensor, Set<?> coordinates) {
        putAllAt(tensor, Position.of(coordinates));
    }

    @Override
    public final void setTensorContext(Context context) {
        Preconditions.checkNotNull(context, "context must not be null");
        checkIfContextValid(context);
        this.context = context;
    }

    private void checkIfContextValid(Context context2) {
        Position contextPosition = context2.getPosition();
        for (Class<?> oneDimensionClass : contextPosition.dimensionSet()) {
            if (dimensions.contains(oneDimensionClass)) {
                throw new IllegalStateException("Inconsistent state: " + oneDimensionClass
                        + " you are trying to put in to context is a BASE dimension of the tensor!");
            }
        }
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<E> at(Set<?> coordinates) {
        return this.at(Position.of(coordinates));
    }

    @SafeVarargs
    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<E> at(Object... coordinates) {
        return this.at(Position.of(coordinates));
    }

    @Override
    public final void putAllAt(Tensor<E> tensor, Position position) {
        checkNotNull(tensor, "The tensor must not be null!");
        checkNotNull(position, "The position must not be null!");
        for (Entry<Position, E> entry : tensor.asMap().entrySet()) {
            putAt(entry.getValue(), Positions.union(position, entry.getKey()));
        }
    }

    @Override
    @SafeVarargs
    public final void putAllAt(Tensor<E> tensor, Object... coordinates) {
        putAllAt(tensor, Position.of(coordinates));
    }

    @Deprecated
    @Override
    public final void put(Tensor.Entry<E> entry) {
        checkNotNull(entry, "Entry to put must not be null!");
        putAt(entry.getValue(), entry.getPosition());
    }

    @Deprecated
    @Override
    public final void putAll(Iterable<Tensor.Entry<E>> newEntries) {
        checkNotNull(newEntries, "Iterable of entries to put must not be null!");
        for (Tensor.Entry<E> entry : newEntries) {
            put(entry);
        }
    }

    public Set<? extends Class<?>> getDimensions() {
        return dimensions;
    }

    public Context getContext() {
        return context;
    }
}