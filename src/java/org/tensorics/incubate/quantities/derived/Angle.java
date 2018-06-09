/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.derived;

import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.annotations.Factor;
import org.tensorics.incubate.quantities.base.Dimensionless;

@Factor(value = Dimensionless.class)
public interface Angle<V> extends Quantity<V> {

}
