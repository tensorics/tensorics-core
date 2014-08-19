/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.util;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A pair of values, which can eg be used as intermediate results in calculations, where the two values are commonly
 * used together. The content is named as 'left' and 'right' since very often, the two values might contain operands of
 * binary operations.
 * <p>
 * Both, the left and right value are not allowed to be {@code null}
 * 
 * @author kfuchsbe
 * @param <V> the type of the values
 */
public final class ValuePair<V> extends AbstractPair<V> {

    /**
     * Private constructor to enforce the use of the factory method.
     * 
     * @param leftValue the left value of the pair
     * @param rightValue the right value of the pair
     */
    private ValuePair(V leftValue, V rightValue) {
        super(checkNotNull(leftValue, "leftValue must not be null"), checkNotNull(rightValue,
                "rightValue must not be null"));
    }

    public static <V> ValuePair<V> fromLeftRight(V leftValue, V rightValue) {
        return new ValuePair<V>(leftValue, rightValue);
    }

}
