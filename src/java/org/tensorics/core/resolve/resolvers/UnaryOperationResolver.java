/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver for all kind of unary operations. It simply calls the operations functional method with the resolved
 * argument.
 * 
 * @author kfuchsbe
 * @param <R> the return type of the unary operation
 */
public class UnaryOperationResolver<R> extends AbstractResolver<R, UnaryOperationExpression<R>> {

    @Override
    public boolean canResolve(UnaryOperationExpression<R> expression, ResolvingContext context) {
        return context.resolves(expression.getOperand());
    }

    @Override
    public R resolve(UnaryOperationExpression<R> expression, ResolvingContext context) {
        R operand = context.resolvedValueOf(expression.getOperand());
        return expression.getOperation().perform(operand);
    }

}
