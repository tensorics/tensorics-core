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
 * This exception is thrown, if a path is searched from a child node to a parent
 * node, but none can be found.
 * 
 * @author kfuchsbe
 */
public class PathDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PathDoesNotExistException(Node childNode, Node ancestorNode) {
		super("The path between child '" + childNode + "' to ancestor '" + ancestorNode + "' contains no elements."
				+ " This means no path could be found."
				+ " Maybe the given child is nowhere in the tree of the ancestor?");
	}

}
