/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.dimensionsOf;

import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.OngoingPut;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;

/**
 * A builder for tensor backed objects, which takes care that only positions which are compatible with the dimensions of
 * the foreseen underlaying tensor are put into it. Internally, it uses a builder for a tensor, to which most of the
 * methods are delegated. At build time, the tensor is encapsulated in the according tensor backed class.
 * <p>
 * This class is not thread safe.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensor (and thus also the tensor backed object)
 * @param <TB> the type of the tensor backed object
 */
public class TensorbackedBuilder<V, TB extends Tensorbacked<V>> {

    private final Class<TB> tensorbackedClass;
    private final ImmutableTensor.Builder<V> tensorBuilder;

    /**
     * Constructor for the builder, which takes the target class of the tensorbacked as an argument. Will only be
     * instantiated by the neighbouring utility class. Therefore it is package private.
     */
    TensorbackedBuilder(Class<TB> tensorbackedClass) {
        this.tensorbackedClass = tensorbackedClass;
        this.tensorBuilder = ImmutableTensor.builder(dimensionsOf(tensorbackedClass));
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<V> at(Position entryPosition) {
        return tensorBuilder.at(entryPosition);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<V> at(Set<?> coordinates) {
        return tensorBuilder.at(coordinates);
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public final OngoingPut<V> at(Object... coordinates) {
        return tensorBuilder.at(coordinates);
    }

    public final void put(Entry<V> entry) {
        tensorBuilder.put(entry);
    }

    public final void putAll(Iterable<Entry<V>> entries) {
        tensorBuilder.putAll(entries);
    }

    public final void putAllAt(Tensor<V> tensor, Position position) {
        tensorBuilder.putAllAt(tensor, position);
    }

    public final void putAllAt(Tensorbacked<V> tensorbacked, Position position) {
        tensorBuilder.putAllAt(tensorbacked.tensor(), position);
    }

    public final void putAllAt(Tensor<V> tensor, Object... coordinates) {
        tensorBuilder.putAllAt(tensor, coordinates);
    }

    public final void putAllAt(Tensorbacked<V> tensorbacked, Object... coordinates) {
        tensorBuilder.putAllAt(tensorbacked.tensor(), coordinates);
    }

    /**
     * Builds the tensor backed object, after all the content is set.
     * 
     * @return a new instance of the tensor backed object, containing all the data as described after instantiating the
     *         builder.
     */
    public TB build() {
        return createBackedByTensor(tensorbackedClass, tensorBuilder.build());
    }

}
