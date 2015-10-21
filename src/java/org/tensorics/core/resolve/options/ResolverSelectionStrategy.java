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
