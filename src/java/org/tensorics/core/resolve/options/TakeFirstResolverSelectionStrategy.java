/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import java.util.List;

import org.tensorics.core.resolve.resolvers.Resolver;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Preconditions;

/**
 * This is the most primitive implementation of a selection strategy for resolvers: It simply picks the first one from
 * the list of available ones.
 * 
 * @author kfuchsbe
 */
public class TakeFirstResolverSelectionStrategy implements ResolverSelectionStrategy {

    @Override
    public <R, E extends Expression<R>> Resolver<R, E> selectResolver(List<Resolver<R, E>> resolvers) {
        Preconditions.checkArgument(!resolvers.isEmpty(), "Resolvers list must not be empty!");
        return resolvers.get(0);
    }

    @Override
    public Class<ResolverSelectionStrategy> getMarkerInterface() {
        return ResolverSelectionStrategy.class;
    }

}
