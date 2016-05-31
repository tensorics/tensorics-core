package org.tensorics.core.iterable.lang;

import org.tensorics.core.iterable.expressions.BinaryPredicateIterableExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class OngoingDeferredIterableBinaryPredicate<S> {

    private final ExtendedField<S> field;
    private final Expression<Iterable<S>> left;

    public OngoingDeferredIterableBinaryPredicate(ExtendedField<S> field, Expression<Iterable<S>> left) {
        this.field = field;
        this.left = left;
    }

    public Expression<Boolean> isLessThan(S value) {
        return isLessThan(ResolvedExpression.of(value));
    }

    public Expression<Boolean> isLessThan(Expression<S> expression) {
        return new BinaryPredicateIterableExpression(field.less(), left, expression);
    }
}
