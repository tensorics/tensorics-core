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

package org.tensorics.core.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationTargetException;

import com.google.common.reflect.Invokable;

/**
 * Can instantiate a certain type of classes, which expect exactly one argument
 * of a certain type in the constructor.
 * <p>
 * This class is immutable and thus reusable and thread safe.
 * 
 * @author kfuchsbe
 * @param <A>
 *            the type of the constructor argument
 * @param <R>
 *            the type of the resulting instance
 */
public abstract class SingleArgumentInvokableInstantiator<A, R> implements Instantiator<A, R> {

	protected final Class<R> instanceClass;
	private final Invokable<R, R> invokable;

	public SingleArgumentInvokableInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
		this.instanceClass = instanceClass;
		this.invokable = invokableFor(constructorArgumentClass);
		this.invokable.setAccessible(true);
	}

	@Override
	public <A1 extends A> R create(A1 argument) {
		checkNotNull(argument, "The constructor argument must not be null!");
		try {
			return invokable.invoke(null, argument);
		} catch (IllegalAccessException | //
				IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalArgumentException(
					"Invokable of class '" + instanceClass + "' could not be invoked with argument '" + argument + "'",
					e);
		}
	}

	protected abstract Invokable<R, R> invokableFor(Class<A> constructorArgumentClass);

}