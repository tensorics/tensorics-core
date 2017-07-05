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

package org.tensorics.core.tree.domain;

/**
 * A container for resolved expressions which can be used by resolvers of expressions that need these results.
 * 
 * @author kfuchsbe
 */
public interface ResolvingContext {

    /**
     * Checks if the expression is resolved within this context. Only if this method returns {@code true}, then it is
     * safe to call the {@link #resolvedValueOf(Expression)} method. An expression is seen as resolved, either if the
     * context knows already the resolved value or, if the given expression is a resolved expression by itself.
     * 
     * @param expression the expression for which to check if it is resolved
     * @return {@code true} if the expression is resolved within the context, {@code false} if not
     */
    <E extends Expression<?>> boolean resolves(E expression);

    /**
     * Retrieves the resolved value for the expression. This action can be completed, if the result of the given
     * expression is already known within the context, or if the expression is a resolved expression itself.
     * <p>
     * An {@link IllegalArgumentException} will be thrown, if the expression cannot be resolved within the context.
     * Therefore it is mandatory to call the {@link #resolves(Expression)} method first to check if the expression is
     * resolved in the context.
     * 
     * @param expression the expression for which to retrieve the resolved value
     * @return the resolved value for the expression
     */
    <R, E extends Expression<R>> R resolvedValueOf(E expression);

    /**
     * Retrieves the amount of resolved values which are stored in the context.
     * 
     * @return the number of stored resolved values
     */
    int size();

}
