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

package org.tensorics.core.fields.doubles;

import org.tensorics.core.math.operations.CommutativeAssociativeOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.math.structures.grouplike.AbstractAbelianGroup;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * Provides mathematical structures for double values (which form a field). Usually, users will not construct instances
 * of this class manually, but use the convenience methods from the {@link Structures} class. The reason is that
 * instances of this class are per se of limited use. They only become useful, if they are extended by additional
 * functionality.
 *
 * @author kfuchsbe
 * @see Structures
 */
public final class DoubleField extends AbstractOrderedField<Double> implements OrderedField<Double> {
    private static final long serialVersionUID = 1L;

    private static final double ZERO = 0.0;
    private static final double ONE = 1.0;

    /**
     * Package visible constructor to allow the structures class to instantiate the class. Use the lookup methods in the
     * {@link Structures} class to retrieve an instance of the field.
     */
    DoubleField() {
        super(new DoubleAdditionGroup(), new DoubleMultiplicationGroup(), new DoubleLessOrEqual());
    }

    static class DoubleLessOrEqual implements BinaryPredicate<Double> {
        @Override
        public boolean test(Double left, Double right) {
            return left <= right;
        }
    }

    private static class DoubleAddition implements CommutativeAssociativeOperation<Double> {
        @Override
        public Double perform(Double left, Double right) {
            return left + right;
        }
    }

    private static class DoubleMultiplication implements CommutativeAssociativeOperation<Double> {
        @Override
        public Double perform(Double left, Double right) {
            return left * right;
        }
    }

    private static class DoubleAdditiveInversion implements UnaryOperation<Double> {
        @Override
        public Double perform(Double value) {
            return -value;
        }
    }

    private static class DoubleMultiplicativeInversion implements UnaryOperation<Double> {
        @Override
        public Double perform(Double value) {
            return 1 / value;
        }
    }

    static class DoubleAdditionGroup extends AbstractAbelianGroup<Double> {
        private static final long serialVersionUID = 1L;

        public DoubleAdditionGroup() {
            super(new DoubleAddition(), ZERO, new DoubleAdditiveInversion());
        }

    }

    static class DoubleMultiplicationGroup extends AbstractAbelianGroup<Double> {
        private static final long serialVersionUID = 1L;

        public DoubleMultiplicationGroup() {
            super(new DoubleMultiplication(), ONE, new DoubleMultiplicativeInversion());
        }

    }

}
