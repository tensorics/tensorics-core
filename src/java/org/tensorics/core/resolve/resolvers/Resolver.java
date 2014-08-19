/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * The version of a resolver, that has its type of objects which it can resolve given in its generic parameters in the
 * class. This is useful for the usage in lower levels of the framework.
 * 
 * @author kfuchsbe
 * @param <R> the type of the resolved type
 * @param <E> the type of the expression that can be resolved
 */
public interface Resolver<R, E extends Expression<R>> {

    boolean canResolve(E expression, ResolvingContext context);

    R resolve(E expression, ResolvingContext context);

    Class<E> getExpressionClass();

}
