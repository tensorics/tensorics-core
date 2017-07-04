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

package org.tensorics.core.testing.hamcrest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorSupport;

/**
 * A hamcrest matcher for unit tests, that matches a values of a tensor to be within a certain tolerance compared to the
 * values of a reference (expected) tensor. Further, the shapes of the two tensors have to be equal (conform with the
 * {@link org.tensorics.core.tensor.Shape#equals(Object)} method).
 * 
 * @author kfuchsbe
 * @param <S> the s
 * @see org.tensorics.core.tensor.Shape
 * @see org.tensorics.core.math.structures.ringlike.Field
 */
public class TensorIsCloseTo<S> extends TypeSafeDiagnosingMatcher<Tensor<S>> {

    private final TensorSupport<S> support;
    private final Tensor<S> expected;
    private final S tolerance;

    public TensorIsCloseTo(TensorSupport<S> tensoricSupport, Tensor<S> value, S tolerance) {
        super();
        this.support = checkNotNull(tensoricSupport, "tensoricSupport must not be null");
        this.expected = checkNotNull(value, "expected must not be null");
        this.tolerance = checkNotNull(tolerance, "tolerance must not be null");
        checkArgument(support.testIf(tolerance).isGreaterOrEqualTo(support.zero()), "Tolerance must be positive.");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("within " + tolerance + " close to other tensor.");
    }

    @Override
    protected boolean matchesSafely(Tensor<S> valueToAssert, Description mismatchDescription) {
        if (!valueToAssert.shape().equals(expected.shape())) {
            mismatchDescription.appendText("Shapes are not equal!");
            return false;
        }

        Map<Position, Mismatch<S>> mismatches = mismatches(valueToAssert);
        if (!mismatches.isEmpty()) {
            mismatchDescription.appendText("the following mismatches were detected (position -> value):\n");
            for (Entry<Position, Mismatch<S>> entry : mismatches.entrySet()) {
                Mismatch<S> mismatch = entry.getValue();
                mismatchDescription.appendText(
                        entry.getKey() + " -> " + mismatch.value + " | expected = " + mismatch.expected + ";\n");
            }
            return false;
        }
        return true;

    }

    private Map<Position, Mismatch<S>> mismatches(Tensor<S> valueToAssert) {
        Map<Position, Mismatch<S>> mismatches = new HashMap<>();
        Tensor<S> diff = support.calculate(valueToAssert).minus(expected);
        for (Position position : diff.shape().positionSet()) {
            S absoluteDiff = support.absoluteValueOf(diff.get(position));
            if (!support.testIf(absoluteDiff).isLessOrEqualTo(tolerance)) {
                mismatches.put(position, new Mismatch<S>(expected.get(position), valueToAssert.get(position)));
            }
        }
        return mismatches;
    }

    private static class Mismatch<S> {
        private final S expected;
        private final S value;

        public Mismatch(S expected, S actual) {
            super();
            this.expected = expected;
            this.value = actual;
        }

    }

}
