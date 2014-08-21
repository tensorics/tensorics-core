/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * @author kfuchsbe
 * @param <E> the type of the elements of the tensor to build
 */
public class AbstractTensorBuilder<E> {

    private final Set<? extends Class<?>> dimensions;
    private final Map<Position, OngoingPut<E>> ongoingPuts = new HashMap<>();
    private final VerificationCallback<E> callback;

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
        if (ongoingPuts.containsKey(entryPosition)) {
            throw new IllegalArgumentException("Already another entry was put at position '" + entryPosition
                    + "'. Duplicate entries are not allowed at the same coordinates.");
        }
        Positions.assertConsistentDimensions(entryPosition, getDimensions());
        OngoingPut<E> ongoingPut = new OngoingPut<E>(entryPosition, callback);
        this.ongoingPuts.put(entryPosition, ongoingPut);
        return ongoingPut;
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
            at(Positions.union(position, entry.getPosition())).put(entry.getValue());
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
        at(entry.getPosition()).put(entry.getValue());
    }

    public final void putAll(Iterable<Tensor.Entry<E>> entries) {
        checkNotNull(entries, "Iterable of entries to put must not be null!");
        for (Tensor.Entry<E> entry : entries) {
            put(entry);
        }
    }

    protected final Set<Tensor.Entry<E>> createEntries() {
        Set<Tensor.Entry<E>> entries = new HashSet<>();
        for (OngoingPut<E> ongoingPut : ongoingPuts.values()) {
            entries.add(ongoingPut.createEntry());
        }
        return entries;
    }

    public Collection<OngoingPut<E>> getPuts() {
        return ongoingPuts.values();
    }

    public Set<? extends Class<?>> getDimensions() {
        return dimensions;
    }
}