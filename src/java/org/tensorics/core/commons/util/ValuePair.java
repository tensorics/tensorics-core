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
