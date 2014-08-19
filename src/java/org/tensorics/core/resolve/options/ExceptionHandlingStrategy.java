/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;


/**
 * A strategy which is used in the dispatcher to decide how exceptions are handled. Possible implementations would
 * either be to handle them in some upper nodes or forward them un-filtered to the client application.
 * 
 * @author kfuchsbe
 */
public interface ExceptionHandlingStrategy extends ResolvingOption {

    void handleWithRootNodeFailingNodeException(ExceptionHandlingRequest parameterObject);
}
