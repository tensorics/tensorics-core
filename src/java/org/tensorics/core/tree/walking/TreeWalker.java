/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * Provides a method to walk through a tree of {@link Node}s. The order of visiting the nodes is defined by the
 * implementation.
 * <p>
 * The {@link #walk(Node, NodeCallback)} method allows to provide callbacks which allows to perform actions at certain
 * moments of the walk-through. The moment when a callback is called depends of the interfaces that are implemented by a
 * specific callback. Possible callbacks and their purposes are:
 * <ul>
 * <li> {@link EveryNodeCallback}: The single method of this callback will be called once for every node in the tree in
 * the order of the walkthrough.
 * <li> {@link BottomNodeCallback}: The single method of this callback will be called for every node in the tree, which
 * is a bottom node (has no children).
 * <li> {@link SkipNodeAndSubTreesCallback}: The method of this callback is called before a node (or its children) are
 * visited. Its sole method can return a boolean flag. If the callback returns {@code true}, then the node (and all the
 * subtree starting from there) is skipped. If {@code false} is returned, the sub tree is processed normally.
 * <li> {@link StepUpCallback}: The sole method of this callback is called when some children of a node were visited and
 * before the actual node is visited.
 * </ul>
 */
public interface TreeWalker {

    /**
     * Walks through the tree starting from the given node and calls back on every node to the given callback
     * 
     * @param startingNode the node where to start walking
     * @param callback the callback to call on each node
     */
    void walk(Node startingNode, NodeCallback callback);

}
