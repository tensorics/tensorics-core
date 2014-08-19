/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
