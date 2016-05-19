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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableMultiset.Builder;
import com.google.common.collect.Multiset;

/**
 * Provides utility methods for reflection based tasks, for example to detect
 * types of generic parameters.
 * 
 * @author kfuchsbe
 */
public final class Classes {

	/**
	 * Private constructor to avoid instantiation
	 */
	private Classes() {
		/* only static methods */
	}

	public static ParameterizedType findGenericSuperclassOfType(Class<?> clazz, Class<?> classToSearch) {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			throw new IllegalArgumentException(
					"The class '" + classToSearch + "' is not a super class of the given class.");
		}

		Type type = clazz.getGenericSuperclass();
		if ((type instanceof ParameterizedType)) {
			if (!superClass.equals(classToSearch)) {
				throw new IllegalArgumentException("The covariant coordinate class does not specify directly the "
						+ "type of coordinate, but seems to use another generic class in the inheritance hierarchy. "
						+ "This is currently not supported.");
			}
			return (ParameterizedType) type;
		}
		return findGenericSuperclassOfType(superClass, classToSearch);
	}

	public static Multiset<Class<?>> classesOf(Iterable<?> coordinates) {
		Builder<Class<?>> builder = ImmutableMultiset.builder();
		for (Object coordinate : coordinates) {
			builder.add(coordinate.getClass());
		}
		return builder.build();
	}

}
