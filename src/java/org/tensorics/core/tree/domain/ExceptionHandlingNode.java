/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;


/**
 * Interface for node that can ultimately handle exception propagation from bottom nodes.
 * 
 * @author agorzaws
 * @param <R> type of handled return type.
 */
public interface ExceptionHandlingNode<R> extends Expression<R> {

    /**
     * @param exception to handle, usually comes from bottom nodes
     * @return a resolved value that shall be used instead of the respective node.
     */
    R handle(Exception exception);
}
