/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import static java.lang.String.format;

import org.tensorics.core.math.predicates.IsEqualTo;
import org.tensorics.core.tree.domain.Expression;

public class IsEqualToExpression<T> extends BinaryPredicateExpression<T> {
    private static final long serialVersionUID = 1L;

    public IsEqualToExpression(Expression<T> left, Expression<T> right) {
        super(new IsEqualTo<T>(), left, right);
    }

    @Override
    public String toString() {
        return format("%s is equal to %s", getLeft(), getRight());
    }
}
