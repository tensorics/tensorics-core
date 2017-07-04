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

/**
 * The unary operation which which calculates the additive inversion of a
 * quantity (aka 'the negative'). Error, validity and the unit of the result are
 * the same as the input quantity, only the value itself will be inverted.
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of scalars on which all the operations are based
 *            (elements of a field)
 */
public class QuantityAdditiveInversion<S> extends QuantityUnaryOperation<S> {

	public QuantityAdditiveInversion(QuantityEnvironment<S> environment) {
		super(environment);
	}

	@Override
	public QuantifiedValue<S> perform(QuantifiedValue<S> scalar) {
		S newValue = environment().field().additiveInversion().perform(scalar.value());
		return Tensorics.quantityOf(newValue, scalar.unit()).withError(scalar.error()).withValidity(scalar.validity());
	}

}
