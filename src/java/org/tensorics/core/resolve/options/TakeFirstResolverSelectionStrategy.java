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
