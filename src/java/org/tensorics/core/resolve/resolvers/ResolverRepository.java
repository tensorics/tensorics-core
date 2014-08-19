/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import java.util.List;

import org.tensorics.core.tree.domain.Expression;

/**
 * Keeps track of available resolvers and provides methods to look them up.
 * 
 * @author kfuchsbe
 */
public interface ResolverRepository {

    /**
     * Has to retrieve all the available resolvers that are applicable for the given expression. Which resolver finally
     * will be used is not to be decided by the repository, but will be decided on higher levels.
     * 
     * @param expression the expression for which to find resolvers
     * @return a list of resolvers, that can resolve the given expression, or an empty list if no applicable ones can
     *         are available.
     */
    <R, E extends Expression<R>> List<Resolver<R, E>> resolversFor(E expression);

}
