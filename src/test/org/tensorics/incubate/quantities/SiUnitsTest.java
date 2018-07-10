/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import static org.tensorics.incubate.quantities.SiUnits.A;
import static org.tensorics.incubate.quantities.SiUnits.C;
import static org.tensorics.incubate.quantities.SiUnits.Hz;
import static org.tensorics.incubate.quantities.SiUnits.K;
import static org.tensorics.incubate.quantities.SiUnits.W;
import static org.tensorics.incubate.quantities.SiUnits.cd;
import static org.tensorics.incubate.quantities.SiUnits.kg;
import static org.tensorics.incubate.quantities.SiUnits.m;
import static org.tensorics.incubate.quantities.SiUnits.mole;
import static org.tensorics.incubate.quantities.SiUnits.s;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.incubate.quantities.any.TensoricAnyQuantityExpressions;

public class SiUnitsTest {

    @Test
    public void test() {
        List<Quantity<?>> units = Arrays.asList(m(), A(), C(), K(), Hz(), kg(), mole(), s(), W(), cd());

        for (Quantity<?> u : units) {
            printUnit(u);
        }

        Expression<Quantity<Object>> w2 = TensoricAnyQuantityExpressions._q(W()).timesQ(W());
        printUnit(w2);
    }

    private void printUnit(Object u) {
        String add = "";
       // if (u instanceof DerivedQuantity) {
            add = "\t-->\t" + QuantityStrings.baseStringFor(u);
       // }
        System.out.println(QuantityStrings.shortStringFor(u) + add);
    }

}
