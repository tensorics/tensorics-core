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

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A condition to test if a quantity is less than another quantity. In case at least one of the quantities has an error,
 * a statistical z-test is done at a given confidence level.
 * 
 * @author mihostet
 * @param <S> the value of the scalar (elements of the field)
 */
public class QuantityLessPredicate<S> extends AbstractQuantityStatisticPredicate<S> {
    final S confidenceLimit;

    public QuantityLessPredicate(QuantityEnvironment<S> environment) {
        super(environment);
        confidenceLimit = inverseGaussianCumulativeDistributionFunction(calculate(one()).minus(
                environment.confidenceLevel()));
    }

    @Override
    public boolean test(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        if (testIf(left.error().or(zero())).isEqualTo(zero()) && testIf(right.error().or(zero())).isEqualTo(zero())) {
            return testIf(subtractQuantities(left, right).value()).isLessThan(zero());
        }
        return testIf(zTestValueForDifference(left, right)).isLessThan(confidenceLimit);
    }
}
