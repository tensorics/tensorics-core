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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The simplest implementation of a resolving context. It is inspired by the original implementation in the TE-MPE
 * analysis package.
 * 
 * @author kfuchsbe
 */
public class ResolvingContextImpl implements EditableResolvingContext {

    private final Map<Object, Object> resolvedExpressions = new ConcurrentHashMap<>();

    @Override
    public void putAllNew(ResolvingContext context) {
        for (Entry<Object, Object> entry : ((ResolvingContextImpl) context).getRebuildingMap().entrySet()) {
            if (!resolvedExpressions.containsKey(entry.getKey())) {
                resolvedExpressions.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int size() {
        return this.resolvedExpressions.size();
    }

    /**
     * Returns a map of instruction nodes to instruction nodes, which contains the nodes to be replaced as key and the
     * nodes which shall replace the old nodes as values.
     * 
     * @return the replacement mapping
     */
    private Map<Object, Object> getRebuildingMap() {
        return new HashMap<>(resolvedExpressions);
    }

    @Override
    public <E extends Expression<?>> boolean resolves(E expression) {
        return expression.isResolved() || resolvedExpressions.containsKey(expression);
    }

    @Override
    public <R, E extends Expression<R>> R resolvedValueOf(E expression) {
        if (expression.isResolved()) {
            return expression.get();
        }

        /* This cast is safe, because we took care when putting the value that it has the right type */
        @SuppressWarnings("unchecked")
        R value = (R) resolvedExpressions.get(expression);

        if (value == null) {
            throw new IllegalArgumentException("No resolved value for expression '" + expression
                    + "' is available. Always use the isResolved() method before to check the availability.");
        }
        return value;
    }

    @Override
    public <R, E extends Expression<R>> void put(E key, R value) {
        resolvedExpressions.put(key, value);
    }

}
