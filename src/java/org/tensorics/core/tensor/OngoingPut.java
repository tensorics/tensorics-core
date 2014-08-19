/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Inner Builder object to manage between positions and values.
 * 
 * @author agorzaws
 * @param <T> type of values
 */
public final class OngoingPut<T> {
    private final Position position;
    private final VerificationCallback<T> verificationCallback;
    private Optional<T> value = Optional.absent();

    OngoingPut(Position position, VerificationCallback<T> verificationCallback) {
        this.position = position;
        this.verificationCallback = verificationCallback;
    }

    /**
     * Allows to assign values to previously set position (set of coordinates)
     * 
     * @param entryValue as value to set
     */
    public void put(T entryValue) {
        Preconditions.checkArgument(entryValue != null, "Argument 'entryValue' must not be null!");
        verificationCallback.verify(entryValue);
        this.value = Optional.of(entryValue);
    }

    Tensor.Entry<T> createEntry() { // NOSONAR
        if (!value.isPresent()) {
            throw new IllegalStateException("The put for position '" + getPosition()
                    + "' was incomplete (no value was set)");
        }
        return new ImmutableEntry<>(getPosition(), value.get());
    }

    public Position getPosition() {
        return position;
    }

    public Optional<T> getValue() {
        return value;
    }

}