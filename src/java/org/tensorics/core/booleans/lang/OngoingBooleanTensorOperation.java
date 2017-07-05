/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.operations.BooleanTensorOperationsRepository;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;

public class OngoingBooleanTensorOperation {

    private final BooleanTensorOperationsRepository repository;
    private Tensor<Boolean> leftTensor;

    public OngoingBooleanTensorOperation(OptionRegistry<ManipulationOption> optionRegistry, Tensor<Boolean> tensor) {
        requireNonNull(optionRegistry, "optionsRegistry must not be null");
        this.repository = new BooleanTensorOperationsRepository(optionRegistry);
        this.leftTensor = requireNonNull(tensor, "left tensor must not be null");
    }

    public Tensor<Boolean> and(Tensor<Boolean> rightTensor) {
        return perform(repository.and(), rightTensor);
    }

    public Tensor<Boolean> or(Tensor<Boolean> rightTensor) {
        return perform(repository.or(), rightTensor);
    }

    public Tensor<Boolean> xor(Tensor<Boolean> rightTensor) {
        return perform(repository.xor(), rightTensor);
    }

    public Tensor<Boolean> nand(Tensor<Boolean> rightTensor) {
        return perform(repository.nand(), rightTensor);
    }

    private Tensor<Boolean> perform(BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> operation,
            Tensor<Boolean> rightTensor) {
        requireNonNull(rightTensor, "rightTensor must not be null");
        return operation.perform(leftTensor, rightTensor);
    }

}
