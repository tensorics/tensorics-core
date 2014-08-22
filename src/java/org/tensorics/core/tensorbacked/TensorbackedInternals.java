/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import static org.tensorics.core.util.Instantiators.instantiatorFor;
import static org.tensorics.core.util.InstantiatorType.CONSTRUCTOR;

import java.util.Set;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.util.InstantiatorType;

import com.google.common.collect.ImmutableSet;

/**
 * This class gives an access to the methods for {@link Tensorbacked} object support.
 * 
 * @author agorzaws, kfuchsbe
 */
public final class TensorbackedInternals {

    private TensorbackedInternals() {
        /* Only static methods */
    }

    /**
     * Retrieves the dimensions from the given class inheriting from tensor backed. This is done by inspecting the
     * {@link Dimensions} annotation.
     * 
     * @param tensorBackedClass the class for which to determine the dimensions
     * @return the set of dimentions (classses of coordinates) which are required to create an instance of the given
     *         class.
     */
    public static Set<Class<?>> dimensionsOf(Class<? extends Tensorbacked<?>> tensorBackedClass) {
        Dimensions dimensionAnnotation = tensorBackedClass.getAnnotation(Dimensions.class);
        if (dimensionAnnotation == null) {
            throw new IllegalArgumentException("No annotation of type '" + Dimensions.class
                    + "' is present on the class '" + tensorBackedClass
                    + "'. Therefore, the dimensions of this tensorbacked type cannot be determined.");
        }
        return ImmutableSet.copyOf(dimensionAnnotation.value());
    }

    /**
     * Creates an instance of a class backed by a tensor.
     * 
     * @param tensorBackedClass the type of the class for which to create an instance.
     * @param tensor the tensor to back the instance
     * @return a new instance of the given class, backed by the given tensor
     */
    public static <V, T extends Tensorbacked<V>> T createBackedByTensor(Class<T> tensorBackedClass, Tensor<V> tensor) {
        verifyDimensions(tensorBackedClass, tensor);
        return instantiatorFor(tensorBackedClass).ofType(CONSTRUCTOR).withArgumentType(Tensor.class).create(tensor);
    }

    private static <T extends Tensorbacked<V>, V> void verifyDimensions(Class<T> tensorBackedClass, Tensor<?> tensor) {
        Set<Class<?>> targetDimensions = dimensionsOf(tensorBackedClass);
        Set<Class<?>> tensorDimensions = tensor.shape().dimensionSet();
        if (!targetDimensions.equals(tensorDimensions)) {
            throw new IllegalArgumentException("Dimensions of target class (" + targetDimensions
                    + ") do not match dimensions of given tensor (" + tensorDimensions + "). Cannot create object.");
        }
    }

}
