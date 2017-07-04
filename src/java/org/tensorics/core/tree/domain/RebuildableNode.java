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

package org.tensorics.core.tree.domain;

/**
 * This interface represents an {@link Node} which can be rebuilt from a
 * context.
 * 
 * @author amoscate
 * @param <T>
 *            instance type of the given {@link Node}
 */
public interface RebuildableNode<T extends Node> extends Node {

	/**
	 * This method rebuild the node whit the new context.
	 * 
	 * @param context
	 *            the {@link RebuildingContext} with which to rebuild the node
	 * @return the new node rebuilt with the children(s) took from the context.
	 */
	T rebuildWithNewChildren(RebuildingContext context);

}
