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

package org.tensorics.core.fields.doubles;

import org.tensorics.core.math.Cheating;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.Math;
import org.tensorics.core.math.impl.ExtendedFieldImpl;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * Utility class that provides methods to access mathematical structures which are available in the tensorics core
 * package.
 * 
 * @author kfuchsbe
 */
public final class Structures {

    private static final DoubleField DOUBLE_FIELD = new DoubleField();
    private static final DoubleMath DOUBLE_MATH = new DoubleMath();
    private static final DoubleCheating DOUBLE_CHEATING = new DoubleCheating();

    /**
     * private constructor to avoid instantiation
     */
    private Structures() {
        /* Only static methods */
    }

    public static ExtendedField<Double> doubles() {
        return extended(DOUBLE_FIELD, DOUBLE_MATH, DOUBLE_CHEATING);
    }

    /**
     * Creates a more explicit view of the given field together with the given implementation of mathematical functions.
     * This way more efficient implementations can be used.
     * 
     * @param field the field for which to create the explicit view.
     * @param math the class providing mathematical functions.
     * @param cheating the class that provides some methods for cheating the field-framework.
     * @return a view on the field, which provides more convenience methods.
     */
    public static <T> ExtendedField<T> extended(OrderedField<T> field, Math<T> math, Cheating<T> cheating) {
        return new ExtendedFieldImpl<>(field, math, cheating);
    }

}
