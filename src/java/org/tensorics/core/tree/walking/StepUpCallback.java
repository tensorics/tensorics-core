/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * If a callback of this type is passed to a tree walker, then its single method is called each time the walker passes
 * from a child node to its parent node.
 * 
 * @author kfuchsbe
 */
public interface StepUpCallback extends NodeCallback {

    void onStepUpFromChildToParent(Node child, Node parent);

}
