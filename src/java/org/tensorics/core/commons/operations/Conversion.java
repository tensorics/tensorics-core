// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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

package org.tensorics.core.commons.operations;

import java.util.function.Function;

/**
 * An operation that converts one type of object into another.
 * 
 * @author kfuchsbe, caguiler
 * @param <T>
 *            the type of the object which shall be converted
 * @param <R>
 *            the return type, i.e. the type into which the object shall be
 *            converted
 */
@FunctionalInterface
public interface Conversion<T, R> extends Function<T, R> {

	/**
	 * Has to implement the conversion logic to convert the given object into an
	 * object of type R.
	 * 
	 * @param object
	 *            the object to convert
	 * @return an object of the correct return type.
	 */
	@Override
	R apply(T object);
}
