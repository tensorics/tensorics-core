package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.lang.TensoricScript;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Resolves a tensoric script to its value. A tensoric script is nothing else then an expression itself, described as a
 * tree of expressions.
 * 
 * @author kfuchsbe
 * @param <V> the type of the field elements on which the tensoric expressions are based
 * @param <R> the return type of the expression
 */
public class TensoricScriptResolver<V, R> extends AbstractResolver<R, TensoricScript<V, R>> {

    @Override
    public boolean canResolve(TensoricScript<V, R> expression, ResolvingContext context) {
        return context.resolves(expression.getInternalExpression());
    }

    @Override
    public R resolve(TensoricScript<V, R> expression, ResolvingContext context) {
        return context.resolvedValueOf(expression.getInternalExpression());
    }

}
