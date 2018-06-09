/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.derived;

import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.annotations.Factor;
import org.tensorics.incubate.quantities.base.Duration;
import org.tensorics.incubate.quantities.base.ElectricCurrent;

@Factor(value = ElectricCurrent.class)
@Factor(value = Duration.class)
public interface ElectricCharge<V> extends Quantity<V> {

}