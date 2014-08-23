/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Default Implementation of {@link Tensor}.
 * <p>
 * By constraint of creation it holds a set of {@link Position} of type C and values of type T, such that ALL Positions
 * contains the same number and type of coordinates. Number and type of coordinates can be accessed and explored via
 * {@link Shape}.
 * <p>
 * There is a special type of Tensor that has ZERO dimensiality. Can be obtained via factory method.
 * <p>
 * {@link ImmutableTensor} is immutable.
 * 
 * @author agorzaws, kfuchsbe
 * @param <T> type of values in Tensor.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class ImmutableTensor<T> implements Tensor<T> {

    private final Map<Position, Tensor.Entry<T>> entries; // NOSONAR
    private final Shape shape; // NOSONAR

    /**
     * Private constructor to be called from builder
     * 
     * @param builder to be used when {@link ImmutableTensor} is created.
     */
    ImmutableTensor(AbstractTensorBuilder<T> builder) {
        this.entries = builder.createEntries();
        this.shape = Shape.viewOf(builder.getDimensions(), this.entries.keySet());
    }

    /**
     * Returns a builder for an {@link ImmutableTensor}. As argument it takes set of class of coordinates which
     * represent the dimensions of the tensor.
     * 
     * @param dimensions a set of classes that can later be used as coordinates for the tensor entries.
     * @return a builder for {@link ImmutableTensor}
     * @param <T> type of values in Tensor.
     */
    public static final <T> Builder<T> builder(Set<? extends Class<?>> dimensions) {
        return new Builder<T>(dimensions);
    }

    /**
     * Returns a builder for an {@link ImmutableTensor}. The dimensions (classes of coordinates) of the future tensor
     * have to be given as arguments here.
     * 
     * @param dimensions the dimensions of the tensor to create
     * @return a builder for an immutable tensor
     * @param <T> the type of values of the tensor
     */
    public static final <T> Builder<T> builder(Class<?>... dimensions) {
        return builder(ImmutableSet.copyOf(dimensions));
    }

    /**
     * Creates a tensor from the given map, where the map has to contain the positions as keys and the values as values.
     * 
     * @param dimensions the desired dimensions of the tensor. This has to be consistent with the position - keys in the
     *            map.
     * @param map the map from which to construct a tensor
     * @return a new immutable tensor
     */
    public static final <T> Tensor<T> fromMap(Set<? extends Class<?>> dimensions, Map<Position, T> map) {
        Builder<T> builder = builder(dimensions);
        for (Map.Entry<Position, T> entry : map.entrySet()) {
            builder.at(entry.getKey()).put(entry.getValue());
        }
        return builder.build();
    }

    /**
     * Returns the builder that can create special tensor of dimension size equal ZERO.
     * 
     * @param value to be used.
     * @return a builder for {@link ImmutableTensor}
     * @param <T> type of values in Tensor.
     */
    public static final <T> Tensor<T> zeroDimensionalOf(T value) {
        Builder<T> builder = builder(Collections.<Class<?>> emptySet());
        builder.at(Position.empty()).put(value);
        return builder.build();
    }

    /**
     * Creates an immutable copy of the given tensor.
     * 
     * @param tensor the tensor whose element to copy
     * @return new immutable Tensor
     */
    public static final <T> Tensor<T> copyOf(Tensor<T> tensor) {
        Builder<T> builder = builder(tensor.shape().dimensionSet());
        builder.putAll(tensor.entrySet());
        return builder.build();
    }

    @Override
    public T get(Position position) {
        return findEntryOrThrow(position).getValue();
    }

    @Override
    public Set<Tensor.Entry<T>> entrySet() {
        return new HashSet<>(this.entries.values());
    }

    @Override
    @SafeVarargs
    public final T get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    @Override
    public Shape shape() {
        return this.shape;
    }

    private ImmutableMap<Position, Tensor.Entry<T>> buildEntryMap(Set<Tensor.Entry<T>> tensorEntries) {
        ImmutableMap.Builder<Position, Tensor.Entry<T>> mapBuilder = ImmutableMap.builder();
        for (Tensor.Entry<T> entry : tensorEntries) {
            mapBuilder.put(entry.getPosition(), entry);
        }
        return mapBuilder.build();
    }

    private Shape buildShape(AbstractTensorBuilder<T> builder, Set<Tensor.Entry<T>> tensorEntries) {
        Shape.Builder shapeBuilder = Shape.builder(builder.getDimensions());
        for (Tensor.Entry<T> entry : tensorEntries) {
            shapeBuilder.add(entry.getPosition());
        }
        return shapeBuilder.build();
    }

    private Tensor.Entry<T> findEntryOrThrow(Position position) {
        Tensor.Entry<T> entry = findEntryOrNull(position);
        if (entry == null) {
            throw new NoSuchElementException("Entry for position '" + position + "' is not contained in this tensor.");
        }
        return entry;
    }

    private Tensor.Entry<T> findEntryOrNull(Position position) {
        return this.entries.get(position);
    }

    /**
     * A builder for an immutable tensor.
     * 
     * @author kfuchsbe
     * @param <S> the type of the values to be added
     */
    public static final class Builder<S> extends AbstractTensorBuilder<S> {

        Builder(Set<? extends Class<?>> dimensions) {
            super(dimensions);
        }

        /**
         * Builds an {@link ImmutableTensor} from all elements put before.
         * 
         * @return an {@link ImmutableTensor}.
         */
        public ImmutableTensor<S> build() {
            return new ImmutableTensor<S>(this);
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Position position : this.shape.positionSet()) {
            buffer.append(position + "=" + get(position) + "; ");
        }
        if (buffer.length() > 1) {
            buffer.setLength(buffer.length() - 2);
        }
        return "{" + buffer + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((shape == null) ? 0 : shape.hashCode());
        return result;
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
        ImmutableTensor<?> other = (ImmutableTensor<?>) obj;
        if (entries == null) {
            if (other.entries != null) {
                return false;
            }
        } else if (!entries.equals(other.entries)) {
            return false;
        }
        if (shape == null) {
            if (other.shape != null) {
                return false;
            }
        } else if (!shape.equals(other.shape)) {
            return false;
        }
        return true;
    }

}
