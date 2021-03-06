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

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.predicates.BinaryPredicate;

/**
 * Represents the mathemetical structure of a field which, in addition to the properties of a field provides a 'total
 * order'. In the given context the total order is defined by a is-less-or-equal predicate (≤). This predicate follows
 * the following properties:
 * <p>
 * <ul>
 * <li>If a ≤ b and b ≤ a then a = b (antisymmetry);
 * <li>If a ≤ b and b ≤ c then a ≤ c (transitivity);
 * <li>a ≤ b or b ≤ a (totality).
 * </ul>
 * For a,b and c being elements of the field.
 * 
 * @author kfuchsbe
 * @param <T> the type of the scalars in the field
 * @see <a href= "http://en.wikipedia.org/wiki/Ordered_field">http://en.wikipedia.org/wiki/Ordered_field</a>
 * @see <a href= "http://en.wikipedia.org/wiki/Total_order">http://en.wikipedia.org/wiki/Total_order</a>
 */
public interface OrderedField<T> extends Field<T> {

    /**
     * Has to retrieve the predicate which represents the less-or-equal condition.
     * 
     * @return a predicate which will returen {@code true} if the left argument is less than the right argument and
     *         {@code false} otherwise.
     */
    BinaryPredicate<T> lessOrEqualPredicate();

}
