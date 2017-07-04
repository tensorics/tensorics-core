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

package org.tensorics.core.quantity.lang;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.conditions.QuantityPedicateRepository;

/**
 * Part of a fluent API clause, which allows to formulate binary conditions on
 * quantified scalar values.
 * 
 * @param <S>
 *            the type of the scalar values
 * @author mihostet
 */
public class OngoingQuantifiedScalarBinaryPredicate<S> {

	private final QuantityPedicateRepository<S> pseudoField;
	private final QuantifiedValue<S> left;

	public OngoingQuantifiedScalarBinaryPredicate(QuantityPedicateRepository<S> pseudoField, QuantifiedValue<S> left) {
		super();
		this.pseudoField = pseudoField;
		this.left = left;
	}

	public boolean isLessThan(QuantifiedValue<S> right) {
		return pseudoField.less().test(left, right);
	}

	public boolean isGreaterThan(QuantifiedValue<S> right) {
		return pseudoField.greater().test(left, right);
	}

}
