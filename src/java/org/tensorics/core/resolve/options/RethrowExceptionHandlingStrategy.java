/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;
import org.tensorics.core.resolve.domain.ResolvingException;

/**
 * This strategy, strictly speaking, does not really handle the exceptions resulting from nodes, but rethrows a
 * resolving exception containing the original one. While, usually, in a production environment one might rarely use
 * such a strategy, it is wealthy in a testing environment, where it should be avoided that newly introduced bugs might
 * be swallowed by 'too defensive' exception handling.
 * 
 * @author kfuchsbe
 */
public class RethrowExceptionHandlingStrategy extends AbstractExceptionHandlingStrategy {

    @Override
    public void handleWithRootNodeFailingNodeException(ExceptionHandlingRequest parameterObject) {
        throw new ResolvingException("Exception occured during processing of node '"
                + parameterObject.getThrowingNode() + "'.", parameterObject.getException());
    }

}
