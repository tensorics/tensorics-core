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
