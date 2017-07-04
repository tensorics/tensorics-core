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
 * If this callback is passed into the tree walker, then it will be called any
 * time, the walker moves from a parent node to a child node. The instance of
 * the callback can decide, if the walker shall continue to walk down to the
 * child node, or not.
 * 
 * @author kfuchsbe
 */
public interface SkipStepDownCallback extends NodeCallback {

	boolean shallSkipStepDownFromParentToChild(Node parentNode, Node childNode);

}
