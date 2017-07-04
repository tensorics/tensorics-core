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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.Path;
import org.tensorics.core.tree.domain.RebuildableNode;
import org.tensorics.core.tree.domain.RebuildingContext;
import org.tensorics.core.tree.domain.RebuildingContextImpl;

import com.google.common.reflect.TypeToken;

/**
 * Provides utility methods to walk through a tree of {@link Node}s.
 * 
 * @author kfuchsbe, agorzaws
 */
public final class Trees {

	private static final TreeWalker PARENT_AFTER_CHILDREN_WALKER = new ParentAfterChildrenWalker();

	/**
	 * private constructor to avoid instantiation
	 */
	private Trees() {
		/* only static methods */
	}

	/**
	 * Traverses the tree, starting from the given node in a way, such that all
	 * children are visited before the parent node is visited. It allows to
	 * provide a callback which will be called at different points of the
	 * traversal, depending on the implemented interfaces of the callback.
	 * 
	 * @see TreeWalker
	 * @param startingNode
	 *            the node which should be considered as the root node for the
	 *            walk-through
	 * @param callback
	 *            the callback which will be called at certain points of the
	 *            traversal, depending on the interfaces which are implemented
	 *            by the callback.
	 * @see NodeCallback
	 */
	public static void walkParentAfterChildren(Node startingNode, NodeCallback callback) {
		PARENT_AFTER_CHILDREN_WALKER.walk(startingNode, callback);
	}

	/**
	 * Retrieves the content of the given subtree. All children will be returned
	 * in the list, before the parent is returned.
	 * 
	 * @param rootNode
	 *            The node which shall be taken as root of the tree.
	 * @return a list containing all nodes of the tree, where all the children
	 *         are put before their common parent node.
	 */
	public static List<Node> subTreeContent(Node rootNode) {
		final List<Node> content = new ArrayList<Node>();
		PARENT_AFTER_CHILDREN_WALKER.walk(rootNode, new EveryNodeCallback() {
			@Override
			public void onEvery(Node node) {
				content.add(node);
			}
		});
		return content;
	}

	/**
	 * Searches in the tree for paths between the two given nodes. The direction
	 * of the path will be from the childNode to the ancestor. The childNode
	 * will be the first element of each path and the given ancestor node the
	 * last element.
	 * 
	 * @param childNode
	 *            the node from which the paths to be found shall start
	 * @param ancestorNode
	 *            the ancestor node, which will be the end node of each path
	 * @return all the paths from childNode to ancestorNode
	 */
	public static List<Path> getPathsFromChildToAncestor(final Node childNode, final Node ancestorNode) {
		final List<Path> paths = new ArrayList<>();
		if (childNode.equals(ancestorNode)) {
			paths.add(new Path(Arrays.asList(childNode)));
			return paths;
		}

		walkParentAfterChildren(ancestorNode, new StepUpCallback() {

			@Override
			public void onStepUpFromChildToParent(Node child, Node parent) {
				if (childNode.equals(child)) {
					Path newPath = new Path();
					newPath.add(child);
					newPath.add(parent);
					paths.add(newPath);
				}

				for (Path path : paths) {
					if (child.equals(getLastElement(path))) {
						path.add(parent);
					}
				}
			}

			private Node getLastElement(Path curentPath) {
				List<Node> nodeList = curentPath.getPath();
				if (!nodeList.isEmpty()) {
					return nodeList.get(nodeList.size() - 1);
				}
				return null;
			}

		});
		if (paths.isEmpty()) {
			throw new PathDoesNotExistException(childNode, ancestorNode);
		}
		return paths;
	}

	/**
	 * Searches for all bottom nodes of the tree. Bottom nodes are those, which
	 * are furthest away from the startingNode on a given branch. (In other
	 * words, those with no more children)
	 * 
	 * @param startingNode
	 *            the node which shall be used as root of the tree.
	 * @return a list of all the nodes which are at the bottom of the tree (i.e.
	 *         which have no children).
	 */
	public static Collection<Node> findBottomNodes(Node startingNode) {
		final Set<Node> bottomNodes = new HashSet<Node>();
		walkParentAfterChildren(startingNode, new BottomNodeCallback() {
			@Override
			public void onBottom(Node node) {
				bottomNodes.add(node);
			}
		});
		return bottomNodes;
	}

	/**
	 * Rebuilds the tree by taking elements from given context.
	 * 
	 * @param rootNode
	 *            the root node of the tree
	 * @param rebuildingContext
	 *            the context containing the elements needed for rebuilding of
	 *            the tree
	 * @return a new root node of rebuilt tree
	 */
	public static <T extends Node> T rebuildTree(T rootNode, final RebuildingContext rebuildingContext) {
		final RebuildingContext newRebuildingContext = new RebuildingContextImpl(rebuildingContext);
		walkParentAfterChildren(rootNode, new EveryNodeCallback() {
			@Override
			public void onEvery(Node node) {
				if ((node instanceof RebuildableNode) && (!newRebuildingContext.containsUpdated(node))) {
					newRebuildingContext.put(node,
							((RebuildableNode<?>) node).rebuildWithNewChildren(newRebuildingContext));
				}
			}
		});
		return newRebuildingContext.getUpdatedOrSame(rootNode);
	}

	/**
	 * walks through the tree, starting from the given rootNode and collects all
	 * the nodes which implement the given class. The given class is not
	 * restricted to s subclass of a node, because it could potentially be a
	 * marker interface. However, the returned set will implement both, Node and
	 * the queried type.
	 * 
	 * @param rootNode
	 *            the node from which to start the search
	 * @param nodeClassToFind
	 *            the class of the nodes to find
	 * @return a set of all found nodes, which implement the given class
	 */
	public static <T> Set<T> findNodesOfClass(Node rootNode, final Class<T> nodeClassToFind) {
		final Set<T> foundNodes = new HashSet<>();
		walkParentAfterChildren(rootNode, new EveryNodeCallback() {
			@Override
			public void onEvery(Node node) {
				if (nodeClassToFind.isInstance(node)) {
					foundNodes.add(nodeClassToFind.cast(node));
				}
			}
		});
		return foundNodes;
	}

	/**
	 * walks through the tree, starting from the given rootNode and collects the
	 * highest node which implement the given class.
	 * 
	 * @param rootNode
	 *            the node from which to start the search
	 * @param nodeClassToFind
	 *            the class of the nodes to find
	 * @return a set of one found Node, which implement the given class
	 */
	public static <T extends Node> Set<? extends T> findHighestNodeOfClass(final Node rootNode,
			final Class<T> nodeClassToFind) {
		final Set<T> foundNodes = new HashSet<>();
		walkParentAfterChildren(rootNode, new EveryNodeCallback() {
			@Override
			public void onEvery(Node node) {
				if (nodeClassToFind.isInstance(node)) {
					foundNodes.clear();
					foundNodes.add(nodeClassToFind.cast(node));
				}
			}
		});
		return foundNodes;
	}

	/**
	 * Searches ancestor nodes which match the given {@link TypeToken} (taking
	 * into account all generic parameters!), starting from the given child not
	 * while using the tree as defined by the given rootNode. This is eg. used
	 * for finding nodes, that can handle Exceptions. If the child node id not
	 * part of the tree then an empty set is returned. If there exist paths
	 * between the two nodes, then it is enforced, that in this paths a matching
	 * node is found. If this is not the case, an exception will be thrown. If
	 * the same node is found in different paths, then the corresponding node is
	 * only contained once in the set.
	 * 
	 * @param childNode
	 *            the node for which the closest ancestors shall be found.
	 * @param rootNode
	 *            the node which defines the root of the tree.
	 * @param nodeToken
	 *            the token, against which the nodes are matched.
	 * @return a set of {@link Node} containing for each path the closest
	 *         {@link Node} that match the given {@link TypeToken}
	 * @throws NoMatchingNodeFoundException
	 *             if there exists a path, that does not contain a matching
	 *             node.
	 */
	public static <T extends Node> Set<T> findClosestAncestorNodeFromNodesToRootOfType(Node childNode, Node rootNode,
			TypeToken<T> nodeToken) throws NoMatchingNodeFoundException {
		List<Path> paths = getPathsFromChildToAncestor(childNode, rootNode);

		Set<T> toReturn = new HashSet<T>();
		for (Path path : paths) {
			T foundNode = findFirstNodeOfType(path.getPath(), nodeToken);
			if (foundNode == null) {
				throw new NoMatchingNodeFoundException(
						"No matching node was found for at least one path. This is not allowed.");
			}
			toReturn.add(foundNode);
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	private static <T> T findFirstNodeOfType(List<Node> currentPath, TypeToken<T> nodeToken) {
		for (Node actualCheckedNode : currentPath) {
			if (nodeToken.isSupertypeOf(actualCheckedNode.getClass())) {
				return (T) actualCheckedNode;
			}
		}
		return null;
	}

}
