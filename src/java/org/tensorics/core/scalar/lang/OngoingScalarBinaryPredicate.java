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

package org.tensorics.core.scalar.lang;

import org.tensorics.core.math.ExplicitField;

/**
 * Part of a fluent API clause, which allows to test binary conditions on scalar values.
 * 
 * @param <S> the type of the scalar values
 * @author kfuchsbe
 */
public class OngoingScalarBinaryPredicate<S> {

    private final ExplicitField<S> field;
    private final S left;

    OngoingScalarBinaryPredicate(ExplicitField<S> field, S left) {
        super();
        this.field = field;
        this.left = left;
    }

    public boolean isEqualTo(S right) {
        return field.equal().test(left, right);
    }

    public boolean isLessThan(S right) {
        return field.less().test(left, right);
    }

    public boolean isLessOrEqualTo(S right) {
        return field.lessOrEqual().test(left, right);
    }

    public boolean isGreaterThan(S right) {
        return field.greater().test(left, right);
    }

    public boolean isGreaterOrEqualTo(S right) {
        return field.greaterOrEqual().test(left, right);
    }

}
