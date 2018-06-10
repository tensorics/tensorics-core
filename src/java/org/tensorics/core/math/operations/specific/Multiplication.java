/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations.specific;

import org.tensorics.core.math.operations.CommutativeAssociativeOperation;
import org.tensorics.incubate.quantities.annotations.Symbol;

@Symbol("*")
public interface Multiplication<T> extends CommutativeAssociativeOperation<T> {

}
