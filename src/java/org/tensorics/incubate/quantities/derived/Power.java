/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.derived;

import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.annotations.Factor;
import org.tensorics.incubate.quantities.base.Duration;
import org.tensorics.incubate.quantities.base.Length;
import org.tensorics.incubate.quantities.base.Mass;

@Factor(value = Mass.class)
@Factor(value = Length.class, exponent = "2")
@Factor(value = Duration.class, exponent = "-3")
public interface Power<V> extends Quantity<V> {

}
