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

package org.tensorics.core.quantity.conditions;

import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A repository for statistic conditions on quantified values at a certain
 * confidence level.
 * 
 * @author mihostet
 * @param <S>
 *            the type of the scalar values (elements of the field)
 */
public class QuantityPedicateRepository<S> {

	private final BinaryPredicate<QuantifiedValue<S>> lessCondition;
	private final BinaryPredicate<QuantifiedValue<S>> greaterCondition;

	public QuantityPedicateRepository(QuantityEnvironment<S> environment) {
		lessCondition = new QuantityLessPredicate<S>(environment);
		greaterCondition = new QuantityGreaterPredicate<S>(environment);
	}

	public BinaryPredicate<QuantifiedValue<S>> less() {
		return lessCondition;
	}

	public BinaryPredicate<QuantifiedValue<S>> greater() {
		return greaterCondition;
	}

}
