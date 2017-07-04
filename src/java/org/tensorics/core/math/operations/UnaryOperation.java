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

package org.tensorics.core.math.operations;

/**
 * The general form of an unitary operation, to be used in algebraic structures.
 * 
 * @author kfuchsbe
 * @param <T>
 *            the type of values for which the unitary operation can be applied
 */
public interface UnaryOperation<T> {

	/**
	 * performs the operation on the given value
	 * 
	 * @param value
	 *            the input value, on which the operation shall be applied
	 * @return the resulting value after the operation
	 */
	T perform(T value);
}
