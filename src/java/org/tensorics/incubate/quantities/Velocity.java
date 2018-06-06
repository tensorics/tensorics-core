/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import org.tensorics.incubate.quantities.annotations.Factor;
import org.tensorics.incubate.quantities.annotations.Symbol;
import org.tensorics.incubate.quantities.base.Duration;
import org.tensorics.incubate.quantities.base.Length;

@Factor(value = Length.class)
@Factor(value = Duration.class, exponent = "-1")
@Symbol("v")
public interface Velocity<V> extends Quantity<V> {

}
