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
