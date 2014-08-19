/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;

/**
 * This interface represents an {@link Node} which can be rebuilt from a context.
 * 
 * @author amoscate
 * @param <T> instance type of the given {@link Node}
 */
public interface RebuildableNode<T extends Node> extends Node {

    /**
     * This method rebuild the node whit the new context.
     * 
     * @param context the {@link RebuildingContext} with which to rebuild the node
     * @return the new node rebuilt with the children(s) took from the context.
     */
    T rebuildWithNewChildren(RebuildingContext context);

}
