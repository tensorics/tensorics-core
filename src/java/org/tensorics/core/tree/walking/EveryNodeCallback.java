/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * A callback for an {@link TreeWalker} that allows to get notified on every visite of a node in the tree.
 * 
 * @author kfuchsbe
 */
public interface EveryNodeCallback extends NodeCallback {

    /**
     * This method will be called by the tree walker, when a node is visited.
     * 
     * @param node the node which is visited
     */
    void onEvery(Node node);

}
