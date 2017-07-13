/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import static java.lang.String.format;

import org.tensorics.core.math.predicates.IsNotEqualTo;
import org.tensorics.core.tree.domain.Expression;

public class IsNotEqualExpression<T> extends BinaryPredicateExpression<T> {
    private static final long serialVersionUID = 1L;

    public IsNotEqualExpression(Expression<T> left, Expression<T> right) {
        super(new IsNotEqualTo<>(), left, right);
    }

    @Override
    public String toString() {
        return format("%s is not equal to %s", getLeft(), getRight());
    }
}
