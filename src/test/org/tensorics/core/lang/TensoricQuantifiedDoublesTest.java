/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.lang.Tensorics.quantityOf;

import javax.measure.unit.SI;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;

public class TensoricQuantifiedDoublesTest {

    @Test
    public void valuesOfMapsToCorrectCoordinates() {
        Tensor<QuantifiedValue<Double>> quantities = Tensorics.<QuantifiedValue<Double>> builder(String.class)
                .put(at("a"), quantityOf(1.0, SI.METER)).put(at("b"), quantityOf(2.0, SI.METER)).build();

        Tensor<Double> centimeters = TensoricDoubles.valuesOf(quantities).inUnitsOf(SI.CENTIMETER);
        Assertions.assertThat(centimeters.get("a")).isEqualTo(100.0);
        Assertions.assertThat(centimeters.get("b")).isEqualTo(200.0);
    }

}
