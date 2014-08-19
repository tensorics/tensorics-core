/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.walking;

/**
 * Marks objects that can be passed into a tree walker, and which are called at a certain stages of the
 * walking-procedure. The exact stage, when the callbacks are called, are defined by the sub types of this interface.
 * 
 * @author kfuchsbe
 */
public interface NodeCallback {
    /* Only a marker interface */
}