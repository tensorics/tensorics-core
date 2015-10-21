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
 * A base class for all kind of deferred (unresolved) expressions. All the methods of the expression interface are
 * implemented here and cannot be overridden. However, the subclasses can / have to provide the necessary informations
 * for the corresponding resolvers.
 * 
 * @author kfuchsbe
 * @param <R> the type of the resolved value of the expression.
 */
public abstract class AbstractDeferredExpression<R> implements Expression<R> {

    private static final boolean RESOLVED = false;

    @Override
    public final boolean isResolved() {
        return RESOLVED;
    }

    @Override
    public final R get() {
        throw new ExpressionIsUnresolvedException("It is not possible to get the value of a deferred expression ('"
                + this.toString() + "')");
    }

}