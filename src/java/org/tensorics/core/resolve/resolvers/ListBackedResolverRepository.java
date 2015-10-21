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
