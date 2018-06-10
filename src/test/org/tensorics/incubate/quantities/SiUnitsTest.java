/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import static org.junit.Assert.*;
import static org.tensorics.incubate.quantities.SiUnits.A;
import static org.tensorics.incubate.quantities.SiUnits.C;
import static org.tensorics.incubate.quantities.SiUnits.Hz;
import static org.tensorics.incubate.quantities.SiUnits.K;
import static org.tensorics.incubate.quantities.SiUnits.W;
import static org.tensorics.incubate.quantities.SiUnits.kg;
import static org.tensorics.incubate.quantities.SiUnits.m;
import static org.tensorics.incubate.quantities.SiUnits.mole;
import static org.tensorics.incubate.quantities.SiUnits.s;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SiUnitsTest {

    @Test
    public void test() {
        List<Quantity<Any>> units = Arrays.asList(m(), A(), C(), K(), Hz(), kg(), mole(), s(), W());

        for (Quantity<Any> u : units) {
            System.out.println(u);
        }
        
    }

}
