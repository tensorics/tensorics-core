/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import java.util.HashSet;
import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * Structural manipulations of tensors (which do not require any knowledge about the mathematical behaviour of the
 * elements).
 * 
 * @author kfuchsbe
 */
public final class TensorStructurals {

    /**
     * Private constructor to avoid instantiation
     */
    private TensorStructurals() {
        /* only static methods */
    }

    public static <V> OngoingTensorManipulation<V> from(Tensor<V> tensor) {
        return new OngoingTensorManipulation<>(tensor);
    }

    /**
     * Merges the given set of the Tensors based on information in their context and dimensions. This operation is only
     * possible, if the following preconditions are fulfilled:
     * <ul>
     * <li>The contexts of all the tensors have THE SAME dimensions</li> AND
     * <li>The dimensions of all the tensors are THE SAME (first found tensor dimension is taken as an reference)</li>
     * </ul
     * 
     * @param tensors to be merged.
     * @return a merged tensor.
     * @throws IllegalArgumentException if zero or one tensor is put to be merged OR if tensors dimensionality and their
     *             context positions dimensionality is not equal OR tensor context is empty.
     */
    public static <E> Tensor<E> merge(Set<Tensor<E>> tensors) {
        if (tensors.size() <= 1) {
            throw new IllegalArgumentException("Cannot merge empty or one element list of tensors!");
        }
        Tensor<E> firstTensor = tensors.iterator().next();
        Set<Class<?>> refDimensionSet = firstTensor.shape().dimensionSet();
        Position refContextPosition = firstTensor.context().getPosition();
        Set<Class<?>> outcomeDimensionSet = new HashSet<>(refDimensionSet);
        Set<Class<?>> dimensionSet = refContextPosition.dimensionSet();
        if (dimensionSet.size() == 0) {
            throw new IllegalArgumentException("Cannot merge tensors with not specified context!");
        }
        outcomeDimensionSet.addAll(dimensionSet);
        Builder<E> tensorBuilder = ImmutableTensor.builder(outcomeDimensionSet);

        for (Tensor<E> oneTensor : tensors) {
            if (TensorStructurals.isValidInTermsOfDimensions(oneTensor, refDimensionSet,
                    refContextPosition.dimensionSet())) {
                tensorBuilder.putAllAt(oneTensor, oneTensor.context().getPosition());
            } else {
                throw new IllegalArgumentException(
                        "One of the tensors in the provided list does not fit the others on dimensions."
                                + " Cannot continiue merging" + oneTensor);
            }
        }
        return tensorBuilder.build();
    }

    /**
     * Merges the given {@link Tensorbacked}s into one {@link Tensorbacked} of the given class. The resulting dimensions
     * must match the dimensions required by the resulting object's class.
     * <p>
     * 
     * @param toBeMerged the tensor backed objects that shall be merged into one
     * @param classToReturn the type of the tensor backed that should be resulting from the merge
     * @return a new tensor backed object resulting from the the merge of the tensors
     */
    public static <TB extends Tensorbacked<E>, TBOUT extends Tensorbacked<E>, E> TBOUT mergeTo(Set<TB> toBeMerged,
            Class<TBOUT> classToReturn) {
        Set<Tensor<E>> internalTensors = new HashSet<>();
        for (TB oneTensorBacked : toBeMerged) {
            internalTensors.add(oneTensorBacked.tensor());
        }
        Tensor<E> tensor = merge(internalTensors);
        return TensorbackedInternals.createBackedByTensor(classToReturn, tensor);

    }

    /**
     * Checks if the provided tensor has: the same dimension as the reference dimensions, equal number of them, as well
     * as equal number of Context Position dimension and referenceContext Position and their dimensionality.
     * 
     * @param tensor the tensor to check
     * @param refDimensions the dimensions that the tensor should have
     * @param refContextDimensions the context position the context dimensions, to which the tensor shall be compatible
     * @return {@code true} if the conditions are fulfilled, {@code false} if not.
     */
    private static <E> boolean isValidInTermsOfDimensions(Tensor<E> tensor, Set<Class<?>> refDimensions,
            Set<Class<?>> refContextDimensions) {
        Set<Class<?>> tensorDimensionSet = tensor.shape().dimensionSet();
        Set<Class<?>> contextPositionDimensionSet = tensor.context().getPosition().dimensionSet();
        return (tensorDimensionSet.equals(refDimensions) && contextPositionDimensionSet.equals(refContextDimensions));
    }

}
