/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
