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

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * This matcher checks, if all the values of the tensor are equal to one specific value. This value might aswell be
 * {@code null}, then then matcher would return true, only if all the elements also would be {@code null}.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of the tensor
 */
public class AllElementsEqualTo<V> extends TypeSafeMatcher<Tensor<V>> {

    private final V value;

    AllElementsEqualTo(V value) {
        this.value = value;
    }

    @Factory
    public static final <V> Matcher<Tensor<V>> allElementsEqualTo(V value) {
        return new AllElementsEqualTo<>(value);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(value);
        description.appendText(" for all tensor elements.");
    }

    @Override
    protected boolean matchesSafely(Tensor<V> tensor) {
        for (Position position : tensor.shape().positionSet()) {
            if (!Objects.equals(value, tensor.get(position))) {
                return false;
            }
        }
        return true;
    }

}
