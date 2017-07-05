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

package org.tensorics.core.iterable.lang;

import org.tensorics.core.iterable.operations.IterableAverage;
import org.tensorics.core.iterable.operations.IterableRms;
import org.tensorics.core.iterable.operations.IterableSize;
import org.tensorics.core.iterable.operations.IterableStd;
import org.tensorics.core.iterable.operations.IterableSum;
import org.tensorics.core.iterable.operations.IterableSumOfSquares;
import org.tensorics.core.iterable.operations.IterableVar;
import org.tensorics.core.math.ExtendedField;

/**
 * Contains instances of operations on iterables, based on a field. The main purpose is to be able to re-use the
 * instances of the operations, in order to avoid to have to re-create them all the time.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which the operations are based.
 */
public class IterableOperationRepository<V> {

    private final IterableAverage<V> iterableAverage;
    private final IterableSize<V> iterableSize;
    private final IterableSum<V> iterableSum;
    private final IterableRms<V> iterableRms;
    private final IterableSumOfSquares<V> iterableSumOfSquares;
    private final IterableVar<V> iterableVar;
    private final IterableStd<V> iterableStd;

    public IterableOperationRepository(ExtendedField<V> field) {
        iterableAverage = new IterableAverage<>(field);
        iterableSize = new IterableSize<>(field);
        iterableSum = new IterableSum<>(field);
        iterableRms = new IterableRms<>(field);
        iterableSumOfSquares = new IterableSumOfSquares<>(field);
        iterableVar = new IterableVar<>(field);
        iterableStd = new IterableStd<>(field);
    }

    public IterableAverage<V> average() {
        return iterableAverage;
    }

    public IterableSize<V> size() {
        return iterableSize;
    }

    public IterableSum<V> sum() {
        return iterableSum;
    }

    public IterableRms<V> rms() {
        return iterableRms;
    }

    public IterableSumOfSquares<V> sumOfSquares() {
        return iterableSumOfSquares;
    }

    public IterableVar<V> var() {
        return iterableVar;
    }

    public IterableStd<V> std() {
        return iterableStd;
    }
}