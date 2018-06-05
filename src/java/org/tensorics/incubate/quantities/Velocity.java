/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import org.tensorics.incubate.quantities.annotations.Factor;

@Factor(value = Length.class)
@Factor(value = Duration.class, exponent = "-1")
public interface Velocity<V> extends Quantity<V> {

}
