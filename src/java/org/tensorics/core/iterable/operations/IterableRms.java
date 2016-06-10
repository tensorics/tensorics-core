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

package org.tensorics.core.iterable.operations;

import static com.google.common.collect.Iterables.isEmpty;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation that takes and iterable of a certain type of values (for which a field has to be provided) and
 * calculates the rms (Root mean square) out of it.
 * <p>
 * For the definition of the root mean square, have a look at <a
 * href="http://en.wikipedia.org/wiki/Root_mean_square">wikipedia</a>.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalars (elements of the field on which the rms will be based)
 */
public class IterableRms<V> extends ScalarSupport<V> implements IterableOperation<V> {

    private final IterableSumOfSquares<V> sumOfSquares;
    private final IterableSize<V> size;

    public IterableRms(ExtendedField<V> field) {
        super(field);
        this.sumOfSquares = new IterableSumOfSquares<>(field);
        this.size = new IterableSize<>(field);
    }

    @Override
    public V apply(Iterable<V> values) {
        if (isEmpty(values)) {
            throw new IllegalArgumentException("r.m.s. of empty value set is not possible.");
        }
        return squareRootOf(calculate(sumOfSquares.apply(values)).dividedBy(size.apply(values)));

    }

}
