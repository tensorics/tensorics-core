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

package org.tensorics.core.quantity.operations;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A base class for binary operations on physical quantities. It keeps the
 * scalar bianary operation to be retrieved by the sub classes.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the scalar values on which all operations are based on
 *            (elements of a field)
 */
public abstract class QuantityBinaryOperation<V> extends AbstractQuantityOperation<V>
		implements BinaryOperation<QuantifiedValue<V>> {

	private final BinaryOperation<V> scalarBinaryOeration;

	protected QuantityBinaryOperation(QuantityEnvironment<V> environment, BinaryOperation<V> scalarBinaryOperation) {
		super(environment);
		this.scalarBinaryOeration = scalarBinaryOperation;
	}

	protected BinaryOperation<V> operation() {
		return scalarBinaryOeration;
	}

}
