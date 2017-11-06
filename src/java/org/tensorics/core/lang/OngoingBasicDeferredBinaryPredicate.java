/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.expressions.BinaryPredicateExpression;
import org.tensorics.core.math.predicates.IsEqualTo;
import org.tensorics.core.math.predicates.IsNotEqualTo;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class OngoingBasicDeferredBinaryPredicate<T> {

    private final Expression<T> left;

    public OngoingBasicDeferredBinaryPredicate(Expression<T> left) {
        this.left = left;
    }

    public Expression<Boolean> isEqualTo(Expression<T> right) {
        return new BinaryPredicateExpression<>(IsEqualTo.isEqualTo(), left, right);
    }

    public Expression<Boolean> isEqualTo(T right) {
        return isEqualTo(ResolvedExpression.of(right));
    }

    public Expression<Boolean> isNotEqualTo(Expression<T> right) {
        return new BinaryPredicateExpression<>(IsNotEqualTo.isNotEqualTo(), left, right);
    }

    public Expression<Boolean> isNotEqualTo(T right) {
        return isNotEqualTo(ResolvedExpression.of(right));
    }

}
