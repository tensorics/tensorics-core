// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
