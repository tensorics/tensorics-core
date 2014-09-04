/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

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
     * Merges the given set of the Tensors based on information in their context and dimensions. The only valid outcome
     * of this operation is the following: <li>Context of all the tensors in the set has THE SAME dimension</li> AND <li>
     * Dimension of all the tensors are THE SAME (first found tensor dimension is taken as an reference)</li>
     * 
     * @param tensors to be merged.
     * @return a merged tensor.
     * @throws IllegalArgumentException if zero or one tensor is put to be merged OR if tensors dimensionality and their
     *             context positions dimensionality is not equal.
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
        outcomeDimensionSet.addAll(dimensionSet);
        Builder<E> tensorBuilder = ImmutableTensor.builder(outcomeDimensionSet);

        for (Tensor<E> oneTensor : tensors) {
            if (isValidInTermsOfDimensions(oneTensor, refDimensionSet, refContextPosition.dimensionSet())) {
                tensorBuilder.putAllAt(oneTensor, oneTensor.context().getPosition());
            } else {
                throw new IllegalArgumentException(
                        "One of the tensors in the provided list does not fit the others on dimensions. Cannot continiue merging");
            }
        }
        return tensorBuilder.build();
    }

    /**
     * Provides the way to merge the set of {@link Tensorbacked} objects to specified object such that the sum of the
     * tensor {@link Dimension} set and tensor context position dimension set is equal to dimension of target class
     * (annotated with {@link Dimensions})
     * 
     * @param toBeMerged
     * @param classToReturn
     * @return
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
     * checks if the provided tensor has: the same dimension as the reference dimensions, equal number of them, as well
     * as equal number of Context Position dimension and referenceContext Position and their dimensionality.
     * 
     * @param tensor
     * @param refDimensions
     * @param refContextPosiotion
     * @return
     */
    private static <E> boolean isValidInTermsOfDimensions(Tensor<E> tensor, Set<Class<?>> refDimensions,
            Set<Class<?>> refContextPosiotion) {
        Set<Class<?>> tensorDimensionSet = tensor.shape().dimensionSet();
        Set<Class<?>> contextPositionDimensionSet = tensor.context().getPosition().dimensionSet();

        return (tensorDimensionSet.size() == refDimensions.size() && tensorDimensionSet.containsAll(refDimensions))
                && (contextPositionDimensionSet.containsAll(refContextPosiotion) && contextPositionDimensionSet.size() == refContextPosiotion
                        .size());
    }

}
