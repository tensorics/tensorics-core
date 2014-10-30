/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.util.Map;
import java.util.Set;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Contains utility methods for tensors
 * 
 * @author kfuchsbe, agorzaws
 */
public final class Tensorics {

    /**
     * private constructor to avoid instantiation
     */
    private Tensorics() {
        /* only static methods */
    }

    /**
     * Creates an instance of a 'support' type class, which provides methods as starting points for the 'fluent' API for
     * tensor manipulation.
     * 
     * @param field the (mathematical construct) field which defines the operations for the tensor elements.
     * @return a tensoric support, which provides the starting methods for the tensoric fluent API.
     * @param <E> the types of values in the field
     */
    public static <E> TensoricSupport<E> using(ExtendedField<E> field) {
        return new TensoricSupport<>(EnvironmentImpl.of(field, ManipulationOptions.defaultOptions(field)));
    }

    /**
     * @see TensorStructurals#merge(Set)
     */
    public static <E> Tensor<E> merge(Set<Tensor<E>> tensors) {
        return TensorStructurals.merge(tensors);
    }

    /**
     * @see TensorStructurals#mergeTo(Set, Class)
     */
    public static <TB extends Tensorbacked<E>, TBOUT extends Tensorbacked<E>, E> TBOUT mergeTo(Set<TB> toBeMerged,
            Class<TBOUT> classToReturn) {
        return TensorStructurals.mergeTo(toBeMerged, classToReturn);
    }

    /**
     * @see ImmutableTensor#builder(Set)
     */
    public static <T> Builder<T> builder(Set<? extends Class<?>> dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    /**
     * @see ImmutableTensor#builder(Class...)
     */
    public static <T> Builder<T> builder(Class<?>... dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    /**
     * @see ImmutableTensor#fromMap(Set, Map)
     */
    public static <T> Tensor<T> fromMap(Set<? extends Class<?>> dimensions, Map<Position, T> map) {
        return ImmutableTensor.fromMap(dimensions, map);
    }

    /**
     * @see ImmutableTensor#copyOf(Tensor)
     */
    public static <T> Tensor<T> copyOf(Tensor<T> tensor) {
        return ImmutableTensor.copyOf(tensor);
    }

    /**
     * @see ImmutableTensor#builderFrom(ImmutableTensor)
     */
    public static <T> Builder<T> builderFrom(ImmutableTensor<T> tensor) {
        return ImmutableTensor.builderFrom(tensor);
    }

    /**
     * @see ImmutableTensor#zeroDimensionalOf(Object)
     */
    public static <T> Tensor<T> zeroDimensionalOf(T value) {
        return ImmutableTensor.zeroDimensionalOf(value);
    }
}
