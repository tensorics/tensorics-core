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

package org.tensorics.core.tensor;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.tensorics.core.commons.util.AbstractPair;
import org.tensorics.core.commons.util.ValuePair;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

/**
 * A pair of tensors which are used together in several occasions.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensors
 */
public final class TensorPair<V> extends AbstractPair<Tensor<V>> {

    /**
     * Private constructor to enforce the usage of the factory method.
     * 
     * @param leftTensor the left tensor of the pair
     * @param rightTensor the right tensor of the pair
     * @throws NullPointerException if one of the arguments is {@code null}
     */
    private TensorPair(Tensor<V> leftTensor, Tensor<V> rightTensor) {
        super(checkNotNull(leftTensor, "leftTensor must not be null"), checkNotNull(rightTensor,
                "rightTensor must not be null"));
    }

    public static <V> TensorPair<V> fromLeftRight(Tensor<V> leftTensor, Tensor<V> rightTensor) {
        return new TensorPair<V>(leftTensor, rightTensor);
    }

    /**
     * Retrieves a pair of values from the two tensors. The left position in the given pair is used to access the left
     * tensor and the right position to access the right tensor. The given position pair must not be null and non-null
     * values have to be present in the tensors. In all other cases, exceptions will be thrown.
     * 
     * @param positionPair the pair of positions for which the values have to be retrieved
     * @return a pair of values, if they exist in the tensors
     * @throws NullPointerException if the given position pair is null or one of the values, retrieved from the tensors
     *             is {@code null}
     */
    public ValuePair<V> get(PositionPair positionPair) {
        checkNotNull(positionPair, "positionPair must not be null");
        V left = left().get(positionPair.left());
        V right = right().get(positionPair.right());
        /* No need to check explicitely for non-null, the ValuePair will do the job */
        return ValuePair.fromLeftRight(left, right);
    }

    /**
     * Extracts all the value pairs of the tensor pair, according to the given iterable of position pairs. The returned
     * list will contain all the value pairs in the order of the position pairs.
     * 
     * @param positionPairs the position pairs for which the value pairs have to be extracte
     * @return a list, containing all the pairs retrieved from the two tensors
     * @throws NullPointerException if the given positionPairs are {@code null}, or no values are contained for at least
     *             one of the positions.
     */
    public List<ValuePair<V>> getAll(Iterable<PositionPair> positionPairs) {
        Preconditions.checkNotNull(positionPairs, "positionPairs must not be null");
        Builder<ValuePair<V>> builder = ImmutableList.builder();
        for (PositionPair positionPair : positionPairs) {
            builder.add(get(positionPair));
        }
        return builder.build();
    }

    /**
     * Extracts pairs of values, according to the given multimap of position pairs and returns them again as a multimap.
     * The map will contain the same keys as the input map and as values all the values indexed by the given position
     * pairs (values of the input map). It is assumed that values exist in the tensors for all given positions. If this
     * is not the case, then an exception will be thrown. The returned value will be a {@link ListMultimap}, because it
     * is easily possible that the returned value pairs are the same for different keys.
     * 
     * @param positionPairs a multimap K: pairs of positions for which to retrieve the values
     * @return a multimap K: pairs of values, extracted from the tensor pair
     */
    public <K> ListMultimap<K, ValuePair<V>> mapValues(Multimap<K, PositionPair> positionPairs) {
        ImmutableListMultimap.Builder<K, ValuePair<V>> builder = ImmutableListMultimap.builder();
        for (Entry<K, Collection<PositionPair>> entry : positionPairs.asMap().entrySet()) {
            builder.putAll(entry.getKey(), getAll(entry.getValue()));
        }
        return builder.build();
    }
}
