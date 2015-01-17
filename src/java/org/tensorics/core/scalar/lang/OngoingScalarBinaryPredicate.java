/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
