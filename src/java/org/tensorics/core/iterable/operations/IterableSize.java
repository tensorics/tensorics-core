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

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * An operation which counts the number of entries in an iterable and returns a scalar, corresponding to the count.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field.
 */
public class IterableSize<V> extends ScalarSupport<V> implements IterableOperation<V> {

    public IterableSize(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V apply(Iterable<V> iterable) {
        V one = one();
        V count = zero();
        for (@SuppressWarnings("unused")
        V value : iterable) {
            count = calculate(count).plus(one);
        }
        return count;
    }

}
