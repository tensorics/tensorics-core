package org.tensorics.core.tree.walking;

/**
 * Might be thrown, if the tree is searched for a certain type (for example, a node that can handle an exception) of
 * node and no corresponding node can be found.
 * 
 * @author kfuchsbe
 */
public class NoMatchingNodeFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoMatchingNodeFoundException(String message) {
        super(message);
    }
}
