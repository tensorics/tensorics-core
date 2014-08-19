/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver that takes binary operations and resolves them to their result. It can only do so, if the two operands are
 * resolved.
 * 
 * @author kfuchsbe
 * @param <R> the return type of the expression
 */
public class BinaryOperationResolver<R> extends AbstractResolver<R, BinaryOperationExpression<R>> {

    @Override
    public boolean canResolve(BinaryOperationExpression<R> expression, ResolvingContext context) {
        return context.resolves(expression.getLeft()) && context.resolves(expression.getRight());
    }

    @Override
    public R resolve(BinaryOperationExpression<R> expression, ResolvingContext context) {
        R left = context.resolvedValueOf(expression.getLeft());
        R right = context.resolvedValueOf(expression.getRight());
        return expression.getOperation().perform(left, right);
    }

}
