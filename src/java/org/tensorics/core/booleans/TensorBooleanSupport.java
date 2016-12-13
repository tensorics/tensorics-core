/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.lang.OngoingBooleanTensorOperation;
import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.tensor.Tensor;

/**
 * A class (to be extended or instantiated) that provides an access to the basic Boolean operations. <br>
 * <b>Note:</b> Use through the delegation class {@link BooleanSupport}
 * 
 * @author agorzaws
 */
public class TensorBooleanSupport {

    private final OptionRegistry<ManipulationOption> optionRegistry;

    public TensorBooleanSupport(OptionRegistry<ManipulationOption> optionRegistry) {
        this.optionRegistry = requireNonNull(optionRegistry);
    }

    public OngoingBooleanTensorOperation calcLogical(Tensor<Boolean> leftTensor) {
        return new OngoingBooleanTensorOperation(optionRegistry, leftTensor);
    }

    public OngoingDetection detectWhere(Tensor<Boolean> tensor) {
        return new OngoingDetection(tensor);
    }

}
