/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.uom;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.incubate.quantities.Any;
import org.tensorics.incubate.quantities.Length;
import org.tensorics.incubate.quantities.Unit;
import org.tensorics.incubate.quantities.Units;

public class UnitsTest {

    private static final String ANY_SYMBOL = "anysym";

    @Test
    public void returnedQuantityIsAUnit() {
        Length<Any> anyLength = Units.unit(Length.class, ANY_SYMBOL);
        Assertions.assertThat(anyLength).isInstanceOf(Unit.class);

        @SuppressWarnings("unchecked")
        Unit<Any> unit = (Unit<Any>) anyLength;
        Assertions.assertThat(unit.symbol()).isEqualTo(ANY_SYMBOL);
    }

}
