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
package org.tensorics.core.lang;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ExpressionIsUnresolvedException;
import org.tensorics.core.tree.domain.Node;

/**
 * This class is intended to be subclassed in order to describe calculations
 * with tensorics, which shall be executed at a later time. This class itself
 * inherits from {@link TensoricExpressionSupport} such that it provides the
 * full capability of the eDSL.
 * <p>
 * To define a script, a field has to be given in the constructor, which
 * describes all the operations which can be performed on its elements.
 * <p>
 * The script logic itself has to be implemented in the {@link #describe()}
 * method. To resolve a script, a
 * {@link org.tensorics.core.resolve.engine.ResolvingEngine} has to be used.
 * 
 * @author kfuchsbe
 * @param <E>
 *            the type of the elements of the field on which all the
 *            calculations are based on.
 * @param <T>
 *            the type which shall be returned by the script, after it is
 *            executed (resolved)
 */
public abstract class TensoricScript<E, T> extends TensoricExpressionSupport<E> implements Expression<T> {

	private static final boolean RESOLVED = false;
	private final Expression<T> internalExpression;

	public TensoricScript(EnvironmentImpl<E> environment) {
		super(environment);
		internalExpression = describe();
	}

	protected abstract Expression<T> describe();

	@Override
	public List<Node> getChildren() {
		return Collections.<Node>singletonList(internalExpression);
	}

	@Override
	public boolean isResolved() {
		return RESOLVED;
	}

	@Override
	public T get() {
		throw new ExpressionIsUnresolvedException();
	}

	public Expression<T> getInternalExpression() {
		return internalExpression;
	}

}
