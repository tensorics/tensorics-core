/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

/**
 * This exception will be thrown by the tree walker, if a loop in the tree might be found. This most probalbly indicates
 * an ill-defined tree and might be due to a programming error.
 * 
 * @author kfuchsbe
 */
public class LoopDetectedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoopDetectedException(String message) {
        super(message);
    }
}
