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

package org.tensorics.core.tensor.variance;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Provides utility methods related to {@link CoContraDimensionPair}s and collections of them.
 * 
 * @author kfuchsbe
 */
public final class CoContraDimensionPairs {

    /**
     * Private constructor to avoid instantiation
     */
    private CoContraDimensionPairs() {
        /* only static methods */
    }

    public static Set<Class<?>> rightDimensionsIn(Iterable<CoContraDimensionPair> pairs) {
        ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
        for (CoContraDimensionPair pair : pairs) {
            builder.add(pair.right());
        }
        return builder.build();
    }

    public static Set<Class<?>> leftDimensionsIn(Iterable<CoContraDimensionPair> pairs) {
        ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
        for (CoContraDimensionPair pair : pairs) {
            builder.add(pair.left());
        }
        return builder.build();
    }

    /**
     * Collects all the dimensions that are contained in at least one of the given pairs, as either left or right
     * dimension.
     * 
     * @param pairs the pairs from which to retrieve the dimensions
     * @return all the dimensions which are involved in at least one of the sets
     */
    public static Set<Class<?>> allDimensionsIn(Iterable<CoContraDimensionPair> pairs) {
        return Sets.union(leftDimensionsIn(pairs), rightDimensionsIn(pairs)).immutableCopy();
    }

    public static Map<Class<?>, Collection<CoContraDimensionPair>> mapOutByContravariantPart(
            List<CoContraDimensionPair> allPairs) {
        ImmutableSetMultimap.Builder<Class<?>, CoContraDimensionPair> builder = ImmutableSetMultimap.builder();
        for (CoContraDimensionPair pair : allPairs) {
            builder.putAll(pair.contravariant(), pair);
        }
        return builder.build().asMap();
    }

    /**
     * Searches in the two given shapes for dimensions that form pairs of co- and contravariant dimensions. This means
     * that the dimension is either covariant in the left shape and contravariant in the right or the other way around.
     * 
     * @param left the left shape of an operation that involves co- contravariant coordinates
     * @param right the right shape of an operation that involves co- and contravariant coordinates
     * @return a (unfiltered) list of pairs of coordinates, that have have co- and contravariant matches in both shapes.
     */
    public static List<CoContraDimensionPair> coContraPairsOf(Shape left, Shape right) {
        List<CoContraDimensionPair> pairs = new ArrayList<>();

        for (Class<?> leftDimension : left.dimensionSet()) {
            for (Class<?> rightDimension : right.dimensionSet()) {
                if (Covariants.isCoContraPair(leftDimension, rightDimension)) {
                    pairs.add(CoContraDimensionPair.ofLeftRight(leftDimension, rightDimension));
                }
            }
        }

        return pairs;
    }

    /**
     * Choses one pair for a type of dimension to use. The current algorithm is:
     * <ul>
     * <li>If only one pair is available (how it should be in most cases), then this one is returned.
     * <li>If two are present, then the one which is covariant on the left is preferred
     * <li>If none is contained in the collection or none which is covariant in the left shape, then an exception is
     * thrown (This is an inconsistent state!)
     * </ul>
     * 
     * @param pairsForOneDimension the pairs from which to choose one
     * @return one pair to use for that dimension
     */
    private static CoContraDimensionPair choose(Collection<CoContraDimensionPair> pairsForOneDimension) {
        checkState(!pairsForOneDimension.isEmpty(),
                "No pairs of dimension found for one dimension-type. " + "Must be some wrong call to this method.");
        if (pairsForOneDimension.size() == 1) {
            return Iterables.getFirst(pairsForOneDimension, null);
        }

        checkState(pairsForOneDimension.size() <= 2, "More then 2 matching co- contravariant dimension found "
                + "for the same contravariant dimension. This should not be possible!?");
        for (CoContraDimensionPair pair : pairsForOneDimension) {
            if (Covariants.isCovariant(pair.left())) {
                return pair;
            }
        }
        throw new IllegalStateException("No valid dimension pair could be found within the collection.");
    }

    private static List<CoContraDimensionPair> chooseOnePerContravariantPart(
            Map<Class<?>, Collection<CoContraDimensionPair>> contravariantToPairMap) {
        List<CoContraDimensionPair> toReturn = new ArrayList<>();
        for (Collection<CoContraDimensionPair> pairsForOneDimension : contravariantToPairMap.values()) {
            CoContraDimensionPair chosenPair = choose(pairsForOneDimension);
            toReturn.add(chosenPair);
        }
        return toReturn;
    }

    /**
     * Filters the list of pairs in such a way, that only one per contravariant type remains.
     * 
     * @param allPairs the pairs to filter
     * @return a list containing only those, which in the end shall be used
     */
    public static List<CoContraDimensionPair> chooseOnePerContravariantPart(List<CoContraDimensionPair> allPairs) {
        return chooseOnePerContravariantPart(mapOutByContravariantPart(allPairs));
    }

    public static Position convertToRight(Position position, List<CoContraDimensionPair> dimensionPairs) {
        Set<Object> convertedCoordinates = new HashSet<>();
        Set<?> remainingCoordinates = new HashSet<>(position.coordinates());
        for (CoContraDimensionPair dimensionPair : dimensionPairs) {
            Object coordinate = position.coordinateFor(dimensionPair.left());
            convertedCoordinates.add(dimensionPair.toRight(coordinate));
            remainingCoordinates.remove(coordinate);
        }
        convertedCoordinates.addAll(remainingCoordinates);
        return Position.of(convertedCoordinates);
    }

}
