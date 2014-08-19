package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.CreationOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver, which takes expressions of creation operations (operations with no input parameters and one result) and
 * resolves them to the respective results.
 * 
 * @author kfuchsbe
 * @param <R> the type of the result of the expression
 */
public class CreationOperationResolver<R> extends AbstractResolver<R, CreationOperationExpression<R>> {

    @Override
    public boolean canResolve(CreationOperationExpression<R> expression, ResolvingContext context) {
        return true;
    }

    @Override
    public R resolve(CreationOperationExpression<R> expression, ResolvingContext context) {
        return expression.getOperation().perform();
    }

}
