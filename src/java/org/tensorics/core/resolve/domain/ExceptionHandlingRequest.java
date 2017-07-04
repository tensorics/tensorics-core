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

package org.tensorics.core.resolve.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Node;

/**
 * Describes the situation that at some point in the expression tree an
 * exception occured which probably should be handled. It contains the
 * information about
 * <ul>
 * <li>the root node of the tree
 * <li>the node, where the exception occures
 * <li>the eception itself
 * <li>and the context, which should be modified as soon as the exception will
 * be handled
 * </ul>
 * <p>
 * This class is immutable
 * 
 * @author kfuchsbe
 */
public final class ExceptionHandlingRequest {
	private final Node rootNode;
	private final Node throwingNode;
	private final RuntimeException exception;
	private final EditableResolvingContext context;

	public ExceptionHandlingRequest(Builder builder) {
		this.rootNode = checkNotNull(builder.rootNode, "rootNode must not be null");
		this.throwingNode = checkNotNull(builder.throwingNode, "throwingNode must not be null");
		this.exception = checkNotNull(builder.exception, "exception must not be null");
		this.context = checkNotNull(builder.context, "context must not be null");
	}

	public static Builder builder() {
		return new Builder();
	}

	public Node getRootNode() {
		return rootNode;
	}

	public RuntimeException getException() {
		return exception;
	}

	public EditableResolvingContext getContext() {
		return context;
	}

	public Node getThrowingNode() {
		return throwingNode;
	}

	/**
	 * The builder for an exception handling request.
	 * <p>
	 * This class is not threadsafe.
	 * 
	 * @author kfuchsbe
	 */
	public static class Builder {
		private Node rootNode;
		private Node throwingNode;
		private RuntimeException exception;
		private EditableResolvingContext context;

		Builder() {
			/* Avoid public instantiation */
		}

		public Builder withRoot(Node newRootNode) {
			this.rootNode = newRootNode;
			return this;
		}

		public Builder withThrowingNode(Node newThrowingNode) {
			this.throwingNode = newThrowingNode;
			return this;
		}

		public Builder withException(RuntimeException newException) {
			this.exception = newException;
			return this;
		}

		public Builder withContext(EditableResolvingContext newContext) {
			this.context = newContext;
			return this;
		}

		public ExceptionHandlingRequest build() {
			return new ExceptionHandlingRequest(this);
		}

	}
}