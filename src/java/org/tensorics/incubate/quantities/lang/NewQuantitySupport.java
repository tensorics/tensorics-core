/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.lang;

import org.tensorics.incubate.quantities.Quantity;

public interface NewQuantitySupport<T> {

    
    <Q extends Quantity<T>> Q q(T value, Q unit);
    
}
