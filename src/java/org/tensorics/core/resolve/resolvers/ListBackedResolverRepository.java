/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import java.util.ArrayList;
import java.util.List;

import org.tensorics.core.tree.domain.Expression;

/**
 * A Resolver repository, which simply holds a list of resolvers for expressions and provides them by looping through
 * the list.
 * 
 * @author kfuchsbe
 */
public class ListBackedResolverRepository implements ResolverRepository {

    private List<Resolver<?, ?>> resolvers = new ArrayList<>();

    @Override
    public <R, E extends Expression<R>> List<Resolver<R, E>> resolversFor(E expression) {
        List<Resolver<R, E>> toReturn = new ArrayList<>();
        for (Resolver<?, ?> resolver : resolvers) {
            if (resolver.getExpressionClass().isAssignableFrom(expression.getClass())) {
                /* XXX Is this safe in all cases? the R type is not checked... */
                @SuppressWarnings("unchecked")
                Resolver<R, E> castedResolver = (Resolver<R, E>) resolver;
                toReturn.add(castedResolver);
            }
        }
        return toReturn;
    }

    public void setResolvers(List<Resolver<?, ?>> resolvers) {
        this.resolvers = new ArrayList<>(resolvers);
    }

}
