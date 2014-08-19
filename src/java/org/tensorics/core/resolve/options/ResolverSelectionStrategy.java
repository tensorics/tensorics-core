/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import java.util.List;

import org.tensorics.core.resolve.resolvers.Resolver;
import org.tensorics.core.tree.domain.Expression;

/**
 * This strategy defines how resolvers are selected, if several possible candidates are available for a given
 * expression. The most primitive choice could e.g. be simply picking the first one. However, in the future (especially
 * for distributed analysis) many more intelligent ways to choose could be thought of. Amongst them e.g. priorization
 * mechanisms, resource awareness or even machine learning techniques (pick the one which was the fasted last time)
 * could be implemented.
 * 
 * @author kfuchsbe
 */
public interface ResolverSelectionStrategy extends ResolvingOption {

    /**
     * Has to select one resolver from the given list.
     * 
     * @param resolvers the resolvers to choose from
     * @return the resolver which shall be used
     */
    <R, E extends Expression<R>> Resolver<R, E> selectResolver(List<Resolver<R, E>> resolvers);

}
