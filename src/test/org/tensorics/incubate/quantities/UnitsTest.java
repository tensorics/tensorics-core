/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class UnitsTest {

    private static final String ANY_SYMBOL = "anysym";

    @Test
    public void returnedQuantityIsAUnit() {
        Length<Any> anyLength = Units.base(Length.class, ANY_SYMBOL);
        Assertions.assertThat(anyLength).isInstanceOf(Unit.class);

        @SuppressWarnings("unchecked")
        Unit<Any> unit = (Unit<Any>) anyLength;
        Assertions.assertThat(unit.symbol()).isEqualTo(ANY_SYMBOL);
    }

}
