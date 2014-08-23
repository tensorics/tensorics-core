/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.tensorics.core.tensor.Tensor.Entry;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @author kfuchsbe
 * @param <E> the type of the elements of the tensor to build
 */
public class AbstractTensorBuilder<E> {

    private final Set<? extends Class<?>> dimensions;
    private final VerificationCallback<E> callback;
    private final ImmutableMap.Builder<Position, Entry<E>> entries = ImmutableMap.builder();

    public AbstractTensorBuilder(Set<? extends Class<?>> dimensions, VerificationCallback<E> callback) {
        Preconditions.checkArgument(dimensions != null, "Argument '" + "dimensions" + "' must not be null!");
        Preconditions.checkArgument(callback != null, "Argument '" + "callback" + "' must not be null!");
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

    public final void putValueAt(E value, Position position) {
        Preconditions.checkNotNull(value, "value must not be null!");
        Preconditions.checkNotNull(position, "position must not be null");

        this.put(new ImmutableEntry<E>(position, value));
    }

    public final void putValueAt(E value, Object... coordinates) {
        this.putValueAt(value, Position.of(coordinates));
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

    /**
     * Puts all the values of the given tensor into the new tensor, at the given position. The positions in the new
     * tensor will be the merged positions of the original coordinates in the tensor with the given target position.
     * Therefore, the given position is not allowed to have a dimensions overlap with the positions in the original
     * tensor.
     * 
     * @param tensor the tensor, whose values to add to the tensor under construction
     * @param position the position which will be merged with the tensor in the source tensor
     */
    /* Not too nice yet. Should be refactored into ongoing put */
    public final void putAllAt(Tensor<E> tensor, Position position) {
        checkNotNull(tensor, "The tensor must not be null!");
        checkNotNull("The position must not be null!");
        for (Tensor.Entry<E> entry : tensor.entrySet()) {
            putValueAt(entry.getValue(), Positions.union(position, entry.getPosition()));
        }
    }

    /**
     * Puts all the values of the given tensor into the new tensor at a position represented by the given coordinates.
     * This is a convenience method for {@link #putAllAt(Tensor, Position)}.
     * 
     * @param tensor the tensor whose values to put into the tensor unser construction
     * @param coordinates the coordinates defining the position where to put the values
     */
    /* Not too nice yet. Should be refactored into ongoing put */
    @SafeVarargs
    public final void putAllAt(Tensor<E> tensor, Object... coordinates) {
        putAllAt(tensor, Position.of(coordinates));
    }

    public final void put(Tensor.Entry<E> entry) {
        checkNotNull(entry, "Entry to put must not be null!");
        Position position = entry.getPosition();
        Positions.assertConsistentDimensions(position, getDimensions());
        this.callback.verify(entry.getValue());
        this.entries.put(position, entry);
    }

    public final void putAll(Iterable<Tensor.Entry<E>> newEntries) {
        checkNotNull(newEntries, "Iterable of entries to put must not be null!");
        for (Tensor.Entry<E> entry : newEntries) {
            put(entry);
        }
    }

    protected final Map<Position, Tensor.Entry<E>> createEntries() {
        return this.entries.build();
    }

    public Set<? extends Class<?>> getDimensions() {
        return dimensions;
    }
}