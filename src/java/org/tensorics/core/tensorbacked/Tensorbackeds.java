/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

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

}
