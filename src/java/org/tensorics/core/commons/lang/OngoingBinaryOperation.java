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
package org.tensorics.core.commons.lang;

/**
 * An interface for parts of the tensorics fluent API to the describe the right
 * hand clause of a binary operation. The main purpose of this interfacce is to
 * streamline the naming for implementations on different types.
 * 
 * @author agorzaws
 * @param <T>
 *            the type of the scalar values on which the operations take place
 */
public interface OngoingBinaryOperation<T> {

	T plus(T rightOperand);

	T minus(T rightOperand);

	T times(T rightOperand);

	T dividedBy(T rightOperand);

	T toThePowerOf(T power);

	T root(T element);

}