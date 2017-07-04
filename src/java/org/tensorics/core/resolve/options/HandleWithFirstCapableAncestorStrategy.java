// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.resolve.options;

import java.util.Set;

import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;
import org.tensorics.core.resolve.domain.ResolvingException;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.ExceptionHandlingNode;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.reflect.TypeToken;

/**
 * When this strategy is used in the dispatcher, then the exceptions which
 * appear at some node, are handled in the first node above which can handle the
 * exception. This handling node is then resolved to an appropriate result and
 * the tree below is not processed further.
 * 
 * @author kfuchsbe
 */
public class HandleWithFirstCapableAncestorStrategy extends AbstractExceptionHandlingStrategy {

	/**
	 * Checks to see if there are nodes to handle each exception that occured
	 * during the processing. If not a {@link ResolvingException} is thrown.
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
							+ parameterObject.getThrowingNode() + "'.",
					parameterObject.getException());
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
