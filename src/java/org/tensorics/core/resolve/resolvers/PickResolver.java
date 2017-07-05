/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.iterable.expressions.PickExpression;
import org.tensorics.core.iterable.expressions.PickExpression.Mode;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Resolver for {@link PickExpression} expression.
 * 
 * @see PickExpression
 * @author acalia
 * @param <T> the type of the data to pick
 */
public class PickResolver<T> extends AbstractResolver<T, PickExpression<T>> {

    @Override
    public boolean canResolve(PickExpression<T> expression, ResolvingContext context) {
        return context.resolves(expression.iterableExpression());
    }

    @Override
    public T resolve(PickExpression<T> expression, ResolvingContext context) {
        Iterable<T> iterable = context.resolvedValueOf(expression.iterableExpression());
        Mode mode = expression.mode();
        int offset = expression.offset();

        return mode.pick(iterable, offset);
    }

}
