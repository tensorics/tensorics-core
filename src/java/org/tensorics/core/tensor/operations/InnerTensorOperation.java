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

package org.tensorics.core.tensor.operations;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.commons.util.ValuePair;
import org.tensorics.core.iterable.operations.IterableOperation;
import org.tensorics.core.iterable.operations.IterableOperations;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.Operations;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.PositionPair;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Positions.DimensionStripper;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.options.BroadcastingStrategy;
import org.tensorics.core.tensor.options.ContextPropagationStrategy;
import org.tensorics.core.tensor.variance.CoContraDimensionPair;
import org.tensorics.core.tensor.variance.CoContraDimensionPairs;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * Represents a binary operation on tensorics, respecting the equivalent of Einstein Summation Convention in tensorics
 * representation. The details are as follows:
 * <p>
 * Since every tensor within the tensorics framework, has to have unique classes for the tensor dimensions (equivalent
 * to the 'indizes' in a usual tensor notation), it is only possible to have at maximum one index (dimension) of the
 * same type in a tensor. Such coordinates are considered per default as contravariant coordinates (indizes). To
 * consistently describe a inner product it is necessary to have corresponding covariant indizes (dimensions in
 * tensorics). This can be achieved by using classes that are derived from the
 * {@link org.tensorics.core.tensor.variance.Covariant} class.
 * <p>
 * The most common of the type of operations, represented by this class, is the inner tensor product. However, this
 * operation is designed to be a bit more general, it uses two provided operations:
 * <ul>
 * <li>A binary operation on the type of the tensor values, by which the the elements of the two tensors are treated.
 * <li>An operation on iterables of the tensor values, which is used to reduce the tensor dimension, if applicable (see
 * below). NOTE: This operation must be associative, because it is performed in all directions at the same time. (The
 * user of this class has to take care about this, but it is currently not enforced by the class design).
 * </ul>
 * Using these preconditions, this operation applies the following rules:
 * <ol>
 * <li>The usual broadcasting and shaping rules are applied (with a modification, which take care about the correct
 * mapping of co- and contravariant dimensions)
 * <li>The element-wise binary operation is applied on all the elements
 * <li>For each dimension, where there is a covariant version in one tensor and a contravariant version in the other
 * tensor, the reduction operation is applied. If one tensor contains both variants and the other only one, then the
 * order the operation still is uniquely defined and the order of the operands (left, right) does not matter. However,
 * if both tensors contain both variants, then the contravariant (default) dimension of the right tensor is reduced
 * together with the covariant dimension of the left tensor. This is equivalent (and can be memorized like) the usual
 * matrix multiplication.
 * <li>The result will always be contravariant
 * </ol>
 * 
 * @author kfuchsbe
 * @param <V>
 * @see org.tensorics.core.tensor.variance.Covariant
 * @see org.tensorics.core.tensor.variance.Covariants
 */
public class InnerTensorOperation<V> implements BinaryOperation<Tensor<V>> {

    private final BinaryOperation<V> elementOperation;
    private final IterableOperation<V> reductionOperation;
    private final OptionRegistry<ManipulationOption> optionRegistry;

    public InnerTensorOperation(BinaryOperation<V> elementOperation, IterableOperation<V> reductionOperation,
            OptionRegistry<ManipulationOption> optionRegistry) {
        super();
        this.elementOperation = elementOperation;
        this.reductionOperation = reductionOperation;
        this.optionRegistry = optionRegistry;
    }

    @Override
    public Tensor<V> perform(Tensor<V> left, Tensor<V> right) {
        checkNotNull(left, "left tensor must not be null!");
        checkNotNull(right, "right tensor must not be null!");

        List<CoContraDimensionPair> allPairs = CoContraDimensionPairs.coContraPairsOf(left.shape(), right.shape());
        List<CoContraDimensionPair> pairsToReduce = CoContraDimensionPairs.chooseOnePerContravariantPart(allPairs);
        Set<Class<?>> dimensionsNotToBroadcast = CoContraDimensionPairs.allDimensionsIn(pairsToReduce);

        TensorPair<V> broadcasted = broadcast(left, right, dimensionsNotToBroadcast);

        Set<Class<?>> leftDimensionsToReduce = CoContraDimensionPairs.leftDimensionsIn(pairsToReduce);
        Set<Class<?>> rightDimensionsToReduce = CoContraDimensionPairs.rightDimensionsIn(pairsToReduce);

        Set<Class<?>> remainingLeftDimensions = Sets
                .difference(broadcasted.left().shape().dimensionSet(), leftDimensionsToReduce).immutableCopy();
        Set<Class<?>> remainingRightDimensions = Sets
                .difference(broadcasted.right().shape().dimensionSet(), rightDimensionsToReduce).immutableCopy();

        Set<Class<?>> targetDimensions = Sets.union(remainingLeftDimensions, remainingRightDimensions).immutableCopy();
        Set<Class<?>> remainingCommonDimensions = Sets.intersection(remainingLeftDimensions, remainingRightDimensions);

        /*
         * produce a multimap from positions, consisting of all but the unique right dimensions to positions.
         */
        Set<Class<?>> uniqueLeftDimensions = Sets.difference(remainingLeftDimensions, remainingCommonDimensions);
        Set<Class<?>> uniqueRightDimensions = Sets.difference(remainingRightDimensions, remainingCommonDimensions);
        Multimap<Position, Position> nonUniqueToRightPositions = Positions
                .mapByStripping(broadcasted.right().shape().positionSet(), uniqueRightDimensions);

        DimensionStripper stripper = Positions.stripping(uniqueLeftDimensions);

        DimensionStripper targetLeftStripper = Positions.stripping(leftDimensionsToReduce);
        DimensionStripper targetRightStripper = Positions.stripping(rightDimensionsToReduce);

        ImmutableMultimap.Builder<Position, PositionPair> builder = ImmutableMultimap.builder();
        for (Position leftPosition : broadcasted.left().shape().positionSet()) {
            Position remainingLeftPosition = targetLeftStripper.apply(leftPosition);

            Position nonUniqueLeftPosition = stripper.apply(leftPosition);
            Position nonUniqueRightPosition = CoContraDimensionPairs.convertToRight(nonUniqueLeftPosition,
                    pairsToReduce);
            Collection<Position> rightPositions = nonUniqueToRightPositions.get(nonUniqueRightPosition);
            for (Position rightPosition : rightPositions) {
                Position remainingRightPosition = targetRightStripper.apply(rightPosition);
                Position targetPosition = Positions.combineDimensions(remainingLeftPosition, remainingRightPosition,
                        targetDimensions);
                builder.put(targetPosition, PositionPair.fromLeftRight(leftPosition, rightPosition));
            }
        }

        Multimap<Position, PositionPair> targetPositionToPairs = builder.build();

        ListMultimap<Position, ValuePair<V>> valuePairs = broadcasted.mapValues(targetPositionToPairs);

        ListMultimap<Position, V> targetPositionToValueSet = Operations.mapAll(valuePairs, elementOperation);

        Map<Position, V> result = IterableOperations.reduce(targetPositionToValueSet, reductionOperation);

        ContextPropagationStrategy cps = optionRegistry.get(ContextPropagationStrategy.class);
        Position resultingContext = cps.contextForLeftRight(left.context(), right.context());

        TensorBuilder<V> finalBuilder = Tensorics.builder(targetDimensions);
        finalBuilder.putAll(result);
        finalBuilder.context(resultingContext);
        return finalBuilder.build();
    }

    private TensorPair<V> broadcast(Tensor<V> left, Tensor<V> right, Set<Class<?>> dimensionsNotToBroadcast) {
        BroadcastingStrategy broadcasting = optionRegistry.get(BroadcastingStrategy.class);
        return broadcasting.broadcast(left, right, dimensionsNotToBroadcast);
    }

}
