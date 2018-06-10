/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import org.tensorics.core.tree.domain.Expression;

public interface DerivedQuantity<V> extends Quantity<V> {

    Expression<Quantity<V>> expression();

}
