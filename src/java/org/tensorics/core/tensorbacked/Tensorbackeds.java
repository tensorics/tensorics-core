// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.tensorbacked;

import static org.tensorics.core.util.Classes.classOf;

import java.util.Set;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.OngoingFlattening;
import org.tensorics.core.tensor.lang.QuantityTensors;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensorbacked.lang.OngoingTensorbackedConstruction;
import org.tensorics.core.tensorbacked.lang.OngoingTensorbackedFiltering;
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
     * Starting point for a fluent clause to construct tensor backed objects by different means from other objects. For
     * example:
     * 
     * <pre>
     * <code>
     *     // Assume that Orbit and OrbitTimeseries are tensorbacked objects
     *     List<> orbits = new ArrayList<>();
     *     // assume the list is filled
     *     OrbitTimeseries orbitTimeseries = construct(OrbitTimeseries.class).byMerging(orbits);
     * </code>
     * </pre>
     * 
     * @param tensorbackedClass the type of the tensorbacked object that should be constructed
     * @return an object which provides further methods to define the construction of the object
     */
    public static <V, TB extends Tensorbacked<V>> OngoingTensorbackedConstruction<V, TB> construct(
            Class<TB> tensorbackedClass) {
        return new OngoingTensorbackedConstruction<>(tensorbackedClass);
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

    /**
     * Retrieves the shape of the tensor backed object.
     * 
     * @param tensorbacked the tensorbacke object from which to retrieve the shape
     * @return the shape of the internal tensor of the object
     */
    public static final Shape shapeOf(Tensorbacked<?> tensorbacked) {
        return tensorbacked.tensor().shape();
    }

    /**
     * Starting for a fluent clause, that allows to flatten one or multiple dimensions of the internal tensor of the
     * tensor backed object into maps or tensors of lists.
     * 
     * @param tensorbacked the tensor backed object whose internal tensor is subject to flattening of values
     * @return an object which allows to further specify the flattening operation
     */
    public static final <S> OngoingFlattening<S> flatten(Tensorbacked<S> tensorbacked) {
        return TensorStructurals.flatten(tensorbacked.tensor());
    }

    /**
     * Retrieves the tensor from each tensorbacked in the given iterable and returns them in a new iterable. The order
     * of iteration is conserved from the input iterable and also duplicated entries are returned in a duplicated
     * manner.
     * 
     * @param tensorbackeds the iterable of tensorbackeds from which to retrieve the tensors
     * @return an iterable containing the tensors from the given iterable of tensorbackeds
     */
    public static final <S> Iterable<Tensor<S>> tensorsOf(Iterable<? extends Tensorbacked<S>> tensorbackeds) {
        return TensorbackedInternals.tensorsOf(tensorbackeds);
    }

    /**
     * Merges the given {@link Tensorbacked}s into one {@link Tensorbacked} of the given class. The resulting dimensions
     * must match the dimensions required by the resulting object's class.
     * <p>
     * 
     * @param toBeMerged the tensor backed objects that shall be merged into one
     * @param classToReturn the type of the tensor backed that should be resulting from the merge
     * @return a new tensor backed object resulting from the the merge of the tensors
     * @deprecated use construct(classToReturn).byMergingTb(toBeMerged);
     * @see #construct(Class)
     */
    @Deprecated
    public static <TB extends Tensorbacked<E>, TBOUT extends Tensorbacked<E>, E> TBOUT mergeTo(Iterable<TB> toBeMerged,
            Class<TBOUT> classToReturn) {
        return construct(classToReturn).byMergingTb(toBeMerged);
    }

    public static final <V, TB extends Tensorbacked<V>> TB stripContext(TB tensorbacked) {
        TB toReturn = builderFor(classOf(tensorbacked)).putAll(tensorbacked.tensor()).build();
        return toReturn;
    }

    public static <V, TB extends Tensorbacked<V>> TB setContext(TB tensorbacked, Context context) {
        return builderFor(classOf(tensorbacked)).withContext(context).putAll(tensorbacked.tensor()).build();
    }

    public static final <V, TB extends Tensorbacked<V>> OngoingTensorbackedFiltering<V, TB> filter(TB tensorbacked) {
        return new OngoingTensorbackedFiltering<>(tensorbacked);
    }

}
