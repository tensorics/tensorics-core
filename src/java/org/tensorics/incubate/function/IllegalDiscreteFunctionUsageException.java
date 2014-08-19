/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.function;

/**
 * This exception is thrown, if a discrete function is use in a wrong way.
 * 
 * @author agorzaws
 */
public class IllegalDiscreteFunctionUsageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalDiscreteFunctionUsageException(String message) {
        super(message);
    }

}
