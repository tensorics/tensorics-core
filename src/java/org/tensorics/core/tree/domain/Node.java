/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;

import java.util.List;

/**
 * A marker interface for any node in the instruction tree, which later will be processed by a tree walker.
 * 
 * @author amoscate
 */
public interface Node {

    /**
     * Has to return the children of the node. This must never return {@code null}
     * 
     * @return a list containing all the children of the node
     */
    List<Node> getChildren();

}