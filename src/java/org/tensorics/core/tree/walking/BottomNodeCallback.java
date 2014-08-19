/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * A callback for the tree walker, which will be called as soon as a bottom node (a node without any children) is
 * reached.
 * 
 * @author kfuchsbe
 */
public interface BottomNodeCallback extends NodeCallback {

    /**
     * This method will be called as soon as the bottom node of a tree is reached.
     * 
     * @param node the bottom node
     */
    void onBottom(Node node);

}
