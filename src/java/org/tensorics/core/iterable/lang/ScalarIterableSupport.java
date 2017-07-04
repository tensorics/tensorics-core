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

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * Provides utility methods for acting on collections of field elements.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the field
 */
public class ScalarIterableSupport<V> extends ScalarSupport<V> {

    private final IterableOperationRepository<V> repository;

    public ScalarIterableSupport(ExtendedField<V> field) {
        super(field);
        this.repository = new IterableOperationRepository<>(field);
    }

    public final V averageOf(Iterable<V> values) {
        return repository.average().apply(values);
    }

    public final V sizeOf(Iterable<V> values) {
        return repository.size().apply(values);
    }

    public final V sumOf(Iterable<V> values) {
        return repository.sum().apply(values);
    }

    public V rmsOf(Iterable<V> values) {
        return repository.rms().apply(values);
    }

    public V sumOfSquaresOf(Iterable<V> values) {
        return repository.sumOfSquares().apply(values);
    }

    public V varOf(Iterable<V> values) {
        return repository.var().apply(values);
    }

    public V stdOf(Iterable<V> values) {
        return repository.std().apply(values);
    }

}
