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

import java.util.Map;

/**
 * Represents a container, which contains a certain amount of results for
 * operations. It will provide them to the layers of resolution to (partly)
 * replace the resolvable context.
 * 
 * @author kfuchsbe
 */
public interface RebuildingContext {

	/**
	 * Returns updated or same(given) node from context.
	 * 
	 * @param node
	 *            to be see if updated.
	 * @return node or updated node from context.
	 * @param <T>
	 *            the type of {@link Node}
	 */
	<T extends Node> T getUpdatedOrSame(T node);

	/**
	 * Returns resolved node of type T or null if is not yet resolved.
	 * 
	 * @param node
	 *            to get the update from
	 * @return the updated node
	 * @param <T>
	 *            the type of {@link Node}
	 */
	<T extends Node> T getUpdated(T node);

	/**
	 * Checks if the context contains already an updated version of the given
	 * node.
	 * 
	 * @param node
	 *            the instruction node for which it shall be checked if an
	 *            updated version exists for it
	 * @return {@code true} if the context contains an updated version,
	 *         {@code false} if not.
	 */
	boolean containsUpdated(Node node);

	/**
	 * Returns a map of instruction nodes to instruction nodes, which contains
	 * the nodes to be replaced as key and the nodes which shall replace the old
	 * nodes as values.
	 * 
	 * @return the replacement mapping
	 */
	Map<Node, Node> getRebuildingMap();

	/**
	 * Holds relation between old node and new node
	 * 
	 * @param oldNode
	 *            as existing node
	 * @param newNode
	 *            as enhanced node
	 * @param <T>
	 *            the type of {@link Node}
	 */
	<T extends Node> void put(T oldNode, T newNode);

}
