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