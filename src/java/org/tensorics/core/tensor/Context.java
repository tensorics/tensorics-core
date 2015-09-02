/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Set;

/**
 * A class that contains a limited (or zero - empty) additional informations about tensor shape/dimensions. <br>
 * <br>
 * List of the coordinates will return instances of This object should remain immutable as it is an unchangeable
 * property of the data held in {@link Tensor}.
 * 
 * @author agorzaws
 */
public final class Context implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Context EMPTY_CONTEXT = Context.of(Position.empty());

    private final Position position;

    private Context(Position position) {
        this.position = checkNotNull(position, "position must not be null");
    }

    /**
     * @return the context content
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param coordinates to be saved in context/position
     * @return a Context
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public static Context of(Set<?> coordinates) {
        return of(Position.of(coordinates));
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static Context of(Object... coordinates) {
        return of(Position.of(coordinates));
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static Context of(Position position) {
        return new Context(position);
    }

    /**
     * @return creates a default empty context.
     */
    public static Context empty() {
        return EMPTY_CONTEXT;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Context other = (Context) obj;
        if (position == null) {
            if (other.position != null) {
                return false;
            }
        } else if (!position.equals(other.position)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Context [position=" + position + "]";
    }
}
