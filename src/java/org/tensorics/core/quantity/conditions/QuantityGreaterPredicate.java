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

import java.io.Serializable;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A condition to test if a quantity is greater than another quantity. In case at least one of the quantities has an
 * error, a statistical z-test is done at a given confidence level.
 *
 * @author mihostet
 * @param <S> the value of the scalar (elements of the field)
 */
public class QuantityGreaterPredicate<S> extends AbstractQuantityStatisticPredicate<S>implements Serializable {
    private static final long serialVersionUID = 1L;

    final S confidenceLimit;

    public QuantityGreaterPredicate(QuantityEnvironment<S> environment) {
        super(environment);
        confidenceLimit = inverseGaussianCumulativeDistributionFunction(environment.confidenceLevel());
    }

    @Override
    public boolean test(QuantifiedValue<S> left, QuantifiedValue<S> right) {
        if (testIf(left.error().or(zero())).isEqualTo(zero()) && testIf(right.error().or(zero())).isEqualTo(zero())) {
            return testIf(subtractQuantities(left, right).value()).isGreaterThan(zero());
        }
        return testIf(zTestValueForDifference(left, right)).isGreaterThan(confidenceLimit);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((confidenceLimit == null) ? 0 : confidenceLimit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        QuantityGreaterPredicate<?> other = (QuantityGreaterPredicate<?>) obj;
        if (confidenceLimit == null) {
            if (other.confidenceLimit != null) {
                return false;
            }
        } else if (!confidenceLimit.equals(other.confidenceLimit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QuantityGreaterPredicate [confidenceLimit=" + confidenceLimit + "]";
    }
}
