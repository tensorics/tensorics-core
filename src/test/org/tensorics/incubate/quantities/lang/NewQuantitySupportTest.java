/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.lang;

import static org.tensorics.core.fields.doubles.Structures.doubles;
import static org.tensorics.core.lang.ManipulationOptions.defaultOptions;
import static org.tensorics.incubate.quantities.SiUnits.m;

import org.junit.Test;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.incubate.quantities.base.Length;

public class NewQuantitySupportTest extends NewFieldBasedQuantitySupportImpl<Double> {

    public NewQuantitySupportTest() {
        super(EnvironmentImpl.of(doubles(), defaultOptions(doubles())));
    }

    @Test
    public void test() {
        Length<Double> length = q(2.0, m());
        
        System.out.println(length);
    }

}
