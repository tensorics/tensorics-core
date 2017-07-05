/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.lang.Tensorics.scalarOf;

import java.util.Collections;

import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor.Builder;

public class TensorEqualityTest {

    private static final String A_VALUE = "aValue";

    @Test
    public void scalarIsEqualToZerodimTensor() {
        assertThat(scalarOf(A_VALUE)).isEqualTo(zeroDimensionalOf(A_VALUE));
    }

    @Test
    public void zerodimTensorIsEqualToScalar() {
        assertThat(zeroDimensionalOf(A_VALUE)).isEqualTo(scalarOf(A_VALUE));
    }

    @Test
    public void differentContextsAreNotEqual() {
        assertThat(zeroDimensionalOf(A_VALUE)).isNotEqualTo(scalarOf(A_VALUE).withContext("aCoord"));
    }

    private static <T> Tensor<T> zeroDimensionalOf(T value) {
        Builder<T> builder = ImmutableTensor.builder(Collections.<Class<?>> emptySet());
        builder.put(Position.empty(), value);
        return builder.build();
    }

}
