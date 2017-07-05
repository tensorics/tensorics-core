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

package org.tensorics.core.tree.walking;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import org.tensorics.core.tree.domain.Node;

/**
 * Walks the tree in the order, that first all children are visited and afterwards the parent.
 * 
 * @author kfuchsbe, maudrain
 */
public class ParentAfterChildrenWalker implements TreeWalker {

    @Override
    public final void walk(Node node, NodeCallback callback) {
        doWalk(node, callback);
    }

    private void doWalk(Node node, NodeCallback callback) {
        checkNotNull(node, "Node must not be null!");
        checkNotNull(callback, "Callback must not be null!");
        if (shallBeSkipped(node, callback)) {
            return;
        }

        Collection<? extends Node> children = checkNotNull(node.getChildren(),
                "Children of node '" + node + "' must not be null!");
        if (children.isEmpty()) {
            onBottomNode(node, callback);
        } else {
            for (Node childNode : children) {
                if (!shallBeSkippedWalkDown(node, childNode, callback)) {
                    doWalk(childNode, callback);
                    onStepUp(node, callback, childNode);
                }
            }
        }

        onEveryNode(node, callback);

    }

    private void onStepUp(Node node, NodeCallback callback, Node childNode) {
        if (callback instanceof StepUpCallback) {
            ((StepUpCallback) callback).onStepUpFromChildToParent(childNode, node);
        }
    }

    private void onBottomNode(Node node, NodeCallback callback) {
        if (callback instanceof BottomNodeCallback) {
            ((BottomNodeCallback) callback).onBottom(node);
        }
    }

    private void onEveryNode(Node node, NodeCallback callback) {
        if (callback instanceof EveryNodeCallback) {
            ((EveryNodeCallback) callback).onEvery(node);
        }
    }

    private boolean shallBeSkipped(Node node, NodeCallback callback) {
        if (callback instanceof SkipNodeAndSubTreesCallback) {
            return ((SkipNodeAndSubTreesCallback) callback).shallBeSkipped(node);
        }
        return false;
    }

    private boolean shallBeSkippedWalkDown(Node parent, Node child, NodeCallback callback) {
        if (callback instanceof SkipStepDownCallback) {
            return ((SkipStepDownCallback) callback).shallSkipStepDownFromParentToChild(parent, child);
        }
        return false;
    }
}
