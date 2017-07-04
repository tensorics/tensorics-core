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

/**
 * An Enum, which describes the different ways how classes (e.g. tensor-backeds)
 * can be instantiated. The following ways are supported:
 * <ul>
 * <li>Instantiation by constructor
 * <li>Instantiation by static factory method
 * </ul>
 * 
 * @author kfuchsbe
 */
public enum InstantiatorType {
	CONSTRUCTOR {
		@Override
		public <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass) {
			return new SingleArgumentConstructorInstantiator<>(instanceClass, argumentClass);
		}
	},
	FACTORY_METHOD {
		@Override
		public <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass) {
			return new SingleArgumentFactoryMethodInstantiator<>(instanceClass, argumentClass);
		}
	};

	public abstract <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass);
}
