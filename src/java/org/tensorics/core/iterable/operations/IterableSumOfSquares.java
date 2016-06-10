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
 * This operations takes an iterable of field elements as input and calculates the sume of them.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field.
 */
public class IterableSumOfSquares<V> extends ScalarSupport<V> implements IterableOperation<V> {

    public IterableSumOfSquares(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V apply(Iterable<V> iterable) {
        V sum = zero();
        for (V value : iterable) {
            sum = calculate(sum).plus(squareOf(value));
        }
        return sum;
    }

}
