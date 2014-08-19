/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * If this type of callback is passed to the tree walker, then it is called on every node, before calling other
 * callbacks or even callbacks of children.
 * 
 * @author kfuchsbe
 */
public interface SkipNodeAndSubTreesCallback extends NodeCallback {

    /**
     * Called before any other callback on this node is called. The instance of the callback can then decide if the
     * walker shall continue to call the other callbacks and visit the children or not.
     * 
     * @param node the node, which was reached by the tree walker
     * @return {@code true} if the node and all is children shall be skipped this walking iteration, {@code false} if
     *         not
     */
    boolean shallBeSkipped(Node node);

}
