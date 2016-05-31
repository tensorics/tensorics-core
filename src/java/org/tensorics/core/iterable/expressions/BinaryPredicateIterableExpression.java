package org.tensorics.core.iterable.expressions;

import com.google.common.collect.ImmutableList;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BinaryPredicateIterableExpression<T> extends AbstractDeferredExpression<Boolean> {

    private final BinaryPredicate<T> predicate;
    private final Expression<Iterable<T>> left;
    private final Expression<T> right;

    public BinaryPredicateIterableExpression(BinaryPredicate<T> predicate, Expression<Iterable<T>> left, Expression<T> right) {
        super();
        this.predicate = checkNotNull(predicate, "Predicate must not be null!");
        this.left = checkNotNull(left, "Left operand must not be null!");
        this.right = checkNotNull(right, "Right operand must not be null!");
    }

    public BinaryPredicate<T> getPredicate() {
        return predicate;
    }

    public Expression<Iterable<T>> getLeft() {
        return left;
    }

    public Expression<T> getRight() {
        return right;
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.of(left, right);
    }

}
