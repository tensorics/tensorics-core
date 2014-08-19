/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

/**
 * @author kfuchsbe
 */
public abstract class AbstractExceptionHandlingStrategy implements ExceptionHandlingStrategy {

    @Override
    public Class<ExceptionHandlingStrategy> getMarkerInterface() {
        return ExceptionHandlingStrategy.class;
    }

}