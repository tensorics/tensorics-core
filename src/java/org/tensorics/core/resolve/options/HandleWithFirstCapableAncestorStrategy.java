/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import java.util.Set;

import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;
import org.tensorics.core.resolve.domain.ResolvingException;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.ExceptionHandlingNode;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.reflect.TypeToken;

/**
 * When this strategy is used in the dispatcher, then the exceptions which appear at some node, are handled in the first
 * node above which can handle the exception. This handling node is then resolved to an appropriate result and the tree
 * below is not processed further.
 * 
 * @author kfuchsbe
 */
public class HandleWithFirstCapableAncestorStrategy extends AbstractExceptionHandlingStrategy {

    /**
     * Checks to see if there are nodes to handle each exception that occured during the processing. If not a
     * {@link ResolvingException} is thrown.
     */
    @Override
    public void handleWithRootNodeFailingNodeException(ExceptionHandlingRequest parameterObject) {
        Set<ExceptionHandlingNode<?>> handlingNodes = Trees.findClosestAncestorNodeFromNodesToRootOfType(
                parameterObject.getThrowingNode(), parameterObject.getRootNode(),
                new TypeToken<ExceptionHandlingNode<?>>() {
                    private static final long serialVersionUID = 1L;
                });

        if (handlingNodes.isEmpty()) {
            throw new ResolvingException(
                    "No node could be found to handle exception which occured during the processing of node '"
                            + parameterObject.getThrowingNode() + "'.", parameterObject.getException());
        }
        for (ExceptionHandlingNode<?> handlingNode : handlingNodes) {
            putHandled(parameterObject.getException(), handlingNode, parameterObject.getContext());
        }
    }

    private <R> void putHandled(RuntimeException exception, ExceptionHandlingNode<R> handlingNode,
            EditableResolvingContext context) {
        R handledErrorResult = handlingNode.handle(exception);
        context.put(handlingNode, handledErrorResult);
    }

}
