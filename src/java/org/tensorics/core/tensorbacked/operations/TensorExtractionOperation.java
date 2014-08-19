/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * An operation which extracts a tensor from a tensor backed instance.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values of a tensor
 * @param <TB> the type of the tensor backed object
 */
public class TensorExtractionOperation<V, TB extends Tensorbacked<V>> implements Conversion<TB, Tensor<V>> {

    @Override
    public Tensor<V> perform(TB tensorbacked) {
        return tensorbacked.tensor();
    }

}
