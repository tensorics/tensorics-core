/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * A resolver for expressions that convert certain type of objects into others.
 * 
 * @author kfuchsbe
 * @param <T> the type of the objects to be converted
 * @param <R> the type of the objects that result from the conversion
 */
public class ConversionOperationResolver<T, R> extends AbstractResolver<R, ConversionOperationExpression<T, R>> {

    @Override
    public boolean canResolve(ConversionOperationExpression<T, R> expression, ResolvingContext context) {
        return context.resolves(expression.getSource());
    }

    @Override
    public R resolve(ConversionOperationExpression<T, R> expression, ResolvingContext context) {
        T source = context.resolvedValueOf(expression.getSource());
        return expression.getOperation().perform(source);
    }

}
