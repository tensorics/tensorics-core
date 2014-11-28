/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
 * @see <a href="http://en.wikipedia.org/wiki/Ordered_field">http://en.wikipedia.org/wiki/Ordered_field</a>
 * @see <a href="http://en.wikipedia.org/wiki/Total_order">http://en.wikipedia.org/wiki/Total_order</a>
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
