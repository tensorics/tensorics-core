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

package org.tensorics.core.math.impl;

import org.tensorics.core.math.Cheating;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * allows to hook in different implementations for more complicated mathematical operations.
 * 
 * @author kfuchsbe
 * @param <T> the type of elements of the field
 */
public class ExtendedFieldImpl<T> extends ExplicitFieldImpl<T> implements ExtendedField<T> {

    private final org.tensorics.core.math.Math<T> math;
    private final Cheating<T> cheatingInstance;
    private final BinaryOperation<T> powerOperation = new BinaryOperation<T>() {
        @Override
        public T perform(T left, T right) {
            return math.pow(left, right);
        }
    };

    public ExtendedFieldImpl(OrderedField<T> field, org.tensorics.core.math.Math<T> math, Cheating<T> cheating) {
        super(field);
        this.math = math;
        this.cheatingInstance = cheating;
    }

    @Override
    public BinaryOperation<T> power() {
        return powerOperation;
    }

    @Override
    public BinaryOperation<T> root() {
        return (T left, T right) -> math.root(left, right);
    }

    /**
     * Returns the instance which provides methods to convert field elements from and to double values. It is intended
     * that this mechanism can be replaced later by a re implementation of the unit-calculations which are fully based
     * on field elements. To avoid heavy usage until then, this method is marked as deprecated.
     * 
     * @return an object providing conversion methods from/to double values
     * @deprecated because it shall be removed as soon as a better way to handle units (based on field elements) is
     *             provided
     */
    @Override
    @Deprecated
    public Cheating<T> cheating() {
        return cheatingInstance;
    }
}
