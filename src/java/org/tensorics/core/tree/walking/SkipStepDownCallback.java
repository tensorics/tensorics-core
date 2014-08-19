/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * If this callback is passed into the tree walker, then it will be called any time, the walker moves from a parent node
 * to a child node. The instance of the callback can decide, if the walker shall continue to walk down to the child
 * node, or not.
 * 
 * @author kfuchsbe
 */
public interface SkipStepDownCallback extends NodeCallback {

    boolean shallSkipStepDownFromParentToChild(Node parentNode, Node childNode);

}
