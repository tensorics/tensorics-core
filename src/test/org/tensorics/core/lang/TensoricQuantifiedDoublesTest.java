/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.lang.Tensorics.quantityOf;

import java.util.List;

import javax.measure.unit.SI;
import javax.measure.unit.Unit;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableList;

public class TensoricQuantifiedDoublesTest {

    @Test
    public void valuesOfMapsToCorrectCoordinates() {
        Tensor<QuantifiedValue<Double>> quantities = Tensorics.<QuantifiedValue<Double>> builder(String.class)
                .put(at("a"), quantityOf(1.0, SI.METER)).put(at("b"), quantityOf(2.0, SI.METER)).build();

        Tensor<Double> centimeters = TensoricDoubles.valuesOf(quantities).inUnitsOf(SI.CENTIMETER);
        Assertions.assertThat(centimeters.get("a")).isEqualTo(100.0);
        Assertions.assertThat(centimeters.get("b")).isEqualTo(200.0);
    }
    
    @Test
    public void averageOfOneValue()  {
        List<QuantifiedValue<Double>> list = ImmutableList.of(Tensorics.quantityOf(1.0, Unit.ONE));
        QuantifiedValue<Double> avg = TensoricDoubles.averageOfQ(list);
        assertThat(avg.value()).isEqualTo(1.0);
        assertThat(avg.error().isPresent()).isFalse();
    }

    @Test
    public void averageOfOneValueWithError()  {
        List<QuantifiedValue<Double>> list = ImmutableList.of(Tensorics.quantityOf(1.0, Unit.ONE).withError(0.0));
        QuantifiedValue<Double> avg = TensoricDoubles.averageOfQ(list);
        assertThat(avg.value()).isEqualTo(1.0);
        assertThat(avg.error().isPresent()).isTrue();
    }

    
}
