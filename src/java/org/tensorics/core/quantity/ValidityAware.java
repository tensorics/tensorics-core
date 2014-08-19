/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

/**
 * Marks an instance that has a flag, if the content of the object is valid or not. The meaning of 'validity' depends on
 * the context. In the context of tensorics, the validity flag is usually used for flagging values of tensors.
 * 
 * @author kfuchsbe
 */
public interface ValidityAware {

    /**
     * Retrieves the validity flag of the object.
     * 
     * @return {@code true} if the content is valid, {@code false} if it is not.
     */
    Boolean validity();
}
