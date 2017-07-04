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
 * A callback for an {@link TreeWalker} that allows to get notified on every
 * visite of a node in the tree.
 * 
 * @author kfuchsbe
 */
public interface EveryNodeCallback extends NodeCallback {

	/**
	 * This method will be called by the tree walker, when a node is visited.
	 * 
	 * @param node
	 *            the node which is visited
	 */
	void onEvery(Node node);

}
