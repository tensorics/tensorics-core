/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.fields.doubles.Structures.doubles;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;

import com.google.common.collect.ImmutableSet;

public class TensorsTest {

    private Tensor<Double> tensor;

    @Before
    public void setUp() throws Exception {
        tensor = ImmutableTensor.<Double> builder(ImmutableSet.of(XCoordinate.class, YCoordinate.class)).build();
    }

    @Test
    public void testTensorReduction() {
        Tensor<Double> reduced = TensorStructurals.from(tensor).reduce(XCoordinate.class).byAveragingIn(doubles());
        assertEquals(2, tensor.shape().dimensionSet().size());
        assertEquals(1, reduced.shape().dimensionSet().size());
    }

    @Test
    public void testTensorReductionInField() {
        Tensor<Double> reduced = TensorStructurals.from(tensor).reduce(XCoordinate.class).byAveragingIn(doubles());
        assertEquals(2, tensor.shape().dimensionSet().size());
        assertEquals(1, reduced.shape().dimensionSet().size());
    }

}
