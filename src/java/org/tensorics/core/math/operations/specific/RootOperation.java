/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.operations.specific;

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.incubate.quantities.annotations.Symbol;

@Symbol("^(1/)")
public interface RootOperation<T> extends BinaryOperation<T> {

}
