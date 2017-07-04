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

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

/**
 * The binary operation which describes the division of two quantities.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values (field elements) on which all the
 *            operations are based on
 */
public class QuantityDivision<S> extends QuantityBinaryOperation<S> {

	public QuantityDivision(QuantityEnvironment<S> environment) {
		super(environment, environment.field().division());
	}

	@Override
	public QuantifiedValue<S> perform(QuantifiedValue<S> left, QuantifiedValue<S> right) {
		S value = operation().perform(left.value(), right.value());
		Unit unit = environment().quantification().divide(left.unit(), right.unit());
		return Tensorics.quantityOf(value, unit).withValidity(validityFor(left, right))
				.withError(productError(left, right));
	}

}
