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

package org.tensorics.core.tensor.stream;

import java.util.Map;
import java.util.function.Predicate;

import org.tensorics.core.tensor.Position;

/**
 * Utility class to produce {@link Predicate}s to use in a stream of {@code Entry<Position, T>}, in particular for the
 * filter() method, in a convenient way.
 * 
 * @author mihostet
 */
public final class TensorStreamFilters {

    private TensorStreamFilters() {
        /* static only */
    }

    public static <T, C> Predicate<Map.Entry<Position, T>> byCoordinateOfType(Class<C> dimension,
            Predicate<C> positionPredicate) {
        return entry -> positionPredicate.test(entry.getKey().coordinateFor(dimension));
    }

    public static <T> Predicate<Map.Entry<Position, T>> byPosition(Predicate<Position> positionPredicate) {
        return entry -> positionPredicate.test(entry.getKey());
    }

    public static <T> Predicate<Map.Entry<Position, T>> byValue(Predicate<T> valuePredicate) {
        return entry -> valuePredicate.test(entry.getValue());
    }
}
