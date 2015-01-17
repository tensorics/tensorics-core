/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import java.util.Set;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.QuantityTensors;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * Contains (public) utility methods for tensor backed objects.
 * 
 * @author kfuchsbe
 */
public final class Tensorbackeds {

    /**
     * Private constructor to avoid instantiation.
     */
    private Tensorbackeds() {
        /* Only static methods */
    }

    /**
     * Creates a new builder for the given tensor backed class. The builder will allow to add content to the tensor
     * backed object and construct it at the end. Only coordinates wich are compatible with the dimensions as annotated
     * in the tensor backed class are allowed.
     * 
     * @param tensorbackedClass the type of tensor backed object to be created.
     * @return a builder for the tensor backed object
     */
    public static <V, TB extends Tensorbacked<V>> TensorbackedBuilder<V, TB> builderFor(Class<TB> tensorbackedClass) {
        return new TensorbackedBuilder<>(tensorbackedClass);
    }

    /**
     * A convenience method to retrieve the size (number of entries) of a tensor backed object. This is nothing else
     * than a shortcut to the size of the underlaying tensor.
     * 
     * @param tensorbacked the tensor backed object for which to retrieve the size
     * @return the size (number of entries) of the object
     */
    public static <TB extends Tensorbacked<?>> int sizeOf(TB tensorbacked) {
        return tensorbacked.tensor().shape().size();
    }

    /**
     * A convenience method to retrieve the number of dimensions of a tensor backed object. This is a shortcut to
     * retrieving the dimensionality of the underlaying tensor of the tensor backed object.
     * 
     * @param tensorbacked the tensor backed object from which to retrieve the dimensions
     * @return the number of dimensions of the tensor backed object
     */
    public static <TB extends Tensorbacked<?>> int dimensionalityOf(TB tensorbacked) {
        return tensorbacked.tensor().shape().dimensionality();
    }

    /**
     * Creates a new empty instance of a tensorbacked class of the given type. This is simply a convenience method for
     * calling {@link TensorbackedBuilder#build()} on an empty builder.
     * 
     * @param tensorbackedClass the class of the tensor backed object to create
     * @return a new empty instance of the tensorbacked object.
     */
    public static <V, TB extends Tensorbacked<V>> TB empty(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor(tensorbackedClass).build();
    }

    /**
     * Retrieves the validities from a tensorbacked object which contains quantities as values. This is a convenience
     * method to calling the {@link QuantityTensors#validitiesOf(Tensor)} method on the tensor contained in the
     * tensorbacked.
     * 
     * @param tensorbacked the tensorbacked class from which to get the validities
     * @return a tensor containing only the validities of the values of the tensorbacked class
     */
    public static <S> Tensor<Boolean> validitiesOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return QuantityTensors.validitiesOf(tensorbacked.tensor());
    }

    /**
     * Retrieves the values of a tensorbacked object which contains quantities as values. This is a convenience method
     * to calling {@link QuantityTensors#valuesOf(Tensor)} on the tensor backing the tensorbacked object.
     * 
     * @param tensorbacked the tensorbacked object from which to retrieve the values
     * @return a tensor containing the values of quantities in the tensorbacked object
     */
    public static <S> Tensor<S> valuesOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return QuantityTensors.valuesOf(tensorbacked.tensor());
    }

    /**
     * Retrieves the errors from the tensorbacked object. This is a convenience method to calling
     * {@link QuantityTensors#errorsOf(Tensor)} on the tensor backing the tensorbacked object.
     * 
     * @param tensorbacked the tensorbacked object from which to retrieve the errors
     * @return a tensor containing the errors of the quantities within the tensorbacked object
     */
    public static <S> Tensor<Optional<S>> errorsOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return QuantityTensors.errorsOf(tensorbacked.tensor());
    }

    /**
     * Retrieves the unit of a tensorbacked object by looking at the underlaying tensor.
     * 
     * @param tensorbacked the tensorbacked object from which to retrieve the unit
     * @return the unit
     * @throws IllegalArgumentException if the unit cannot be determined
     */
    public static <S> Unit unitOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return QuantityTensors.unitOf(tensorbacked.tensor());
    }

    /**
     * Retrieves a set of all positions within a tensorbacked class.
     * 
     * @param tensorbacked the tensor backed object
     * @return a set containing all positions of the tensorbacked object
     */
    public static Set<Position> positionsOf(Tensorbacked<?> tensorbacked) {
        return tensorbacked.tensor().shape().positionSet();
    }

}
