/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.domain;

/**
 * This exception can be thrown at any time in the processing when a problem occures. It is recommended to specialiye
 * this exception for dedicated cases.
 * 
 * @author kfuchsbe
 */
public class ResolvingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResolvingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResolvingException(String message) {
        super(message);
    }

    public ResolvingException(Throwable cause) {
        super(cause);
    }

}
