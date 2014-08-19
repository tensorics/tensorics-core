/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * This interface defines the type of the operation for {@link Tensorbacked} objects.
 * 
 * @author agorzaws
 * @param <V>
 * @param <TB>
 */
public interface TensorBackedUnaryOperation<V, TB extends Tensorbacked<V>> extends UnaryOperation<TB> {
    /* Only a marker */
}
