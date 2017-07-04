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

import com.google.common.reflect.Invokable;

/**
 * This instantiator expects a public constructor with a single argument of a
 * certain type to instantiate a certain class.
 * 
 * @author kfuchsbe
 * @param <A>
 *            the type of the argument of the constructor
 * @param <R>
 *            the type of the return type (type of the class to instantiate)
 */
public class SingleArgumentConstructorInstantiator<A, R> extends SingleArgumentInvokableInstantiator<A, R> {

	public SingleArgumentConstructorInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
		super(instanceClass, constructorArgumentClass);
	}

	@Override
	protected Invokable<R, R> invokableFor(Class<A> constructorArgumentClass) {
		try {
			return Invokable.from(instanceClass.getConstructor(constructorArgumentClass));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException("The class '" + instanceClass
					+ "' does not have a public constructor requiring exactly one argument of type '"
					+ constructorArgumentClass + "'.", e);
		}
	}

}
