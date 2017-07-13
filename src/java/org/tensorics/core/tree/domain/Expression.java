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

import java.io.Serializable;

/**
 * An expression is a placeholder for concrete value (called 'resolved' in the following) or a value which has to still
 * be evaluated (deferred).
 *
 * @author kaifox
 * @param <R> the type of the resulting value
 */
public interface Expression<R> extends Node, Serializable {

    /**
     * returns {@code true} if the expression contains a concrete value. Thus the method {@link #get()} can be used to
     * retrieve the actual value. If this method returns {@code false}, then the get method will throw an exception.
     *
     * @return {@code true} if the value can be retrieved, {@code false} otherwise.
     */
    boolean isResolved();

    /**
     * Retrieves the value of the expression. If the expression is deferred, then an expression will be thrown.
     *
     * @return the value of the expression.
     * @throws ExpressionIsUnresolvedException if the value is not determined.
     */
    R get();
}
