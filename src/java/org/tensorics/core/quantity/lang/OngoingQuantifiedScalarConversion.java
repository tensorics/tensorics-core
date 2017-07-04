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

import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Part of a fluent clause to convert quantities into each other .
 * 
 * @author kfuchsbe
 * @param <S>
 *            the type of the scalar values of the quantities
 */
public class OngoingQuantifiedScalarConversion<S> {

	private final QuantifiedValue<S> left;
	private final QuantificationStrategy<S> quantification;

	public OngoingQuantifiedScalarConversion(QuantifiedValue<S> left, QuantificationStrategy<S> quantification) {
		super();
		this.left = left;
		this.quantification = quantification;
	}

	public QuantifiedValue<S> to(Unit targetUnit) {
		ErronousValue<S> converted = quantification.convertValueToUnit(left, targetUnit);
		return ImmutableQuantifiedValue.of(converted.value(), targetUnit).withError(converted.error())
				.withValidity(left.validity());
	}

	public QuantifiedValue<S> to(javax.measure.unit.Unit<?> targetUnit) {
		return to(JScienceUnit.of(targetUnit));
	}

}
