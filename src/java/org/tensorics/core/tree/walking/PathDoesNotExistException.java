/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

import org.tensorics.core.tree.domain.Node;

/**
 * This exception is thrown, if a path is searched from a child node to a parent node, but none can be found.
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
