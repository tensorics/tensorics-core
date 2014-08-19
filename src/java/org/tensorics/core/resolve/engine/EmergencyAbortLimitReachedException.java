/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine;

import org.tensorics.core.resolve.domain.ResolvingException;

/**
 * This exception will be thrown as soon as the number of iterations of the tree walker reach a certain limit (as
 * defined there). This usually means some inconsistency within the tree.
 * 
 * @author kfuchsbe
 */
public class EmergencyAbortLimitReachedException extends ResolvingException {
    private static final long serialVersionUID = 1L;

    public EmergencyAbortLimitReachedException(int maxLoopCount) {
        super("The number of iterations reached the emergency limit of " + maxLoopCount
                + ". This means that the tree could not be resolved. "
                + "Reasons for that might be e.g. missing implementations or missing "
                + "'equals' methods in node implementations.");
    }
}
