/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import static org.tensorics.core.booleans.operations.LogicalTensorOperationsRepository.defaultRegistry;

import org.tensorics.core.booleans.operations.LogicalTensorOperationsRepository;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.tensor.Tensor;

public class OngoingTensorBooleanAlgebra {

    private Tensor<Boolean> tensor;

    public OngoingTensorBooleanAlgebra(Tensor<Boolean> tensor) {
        this.tensor = tensor;
    }

    public OngoingTensorAwareBooleanAlgebra and() {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, LogicalTensorOperationsRepository.and(defaultRegistry()));
    }

    public OngoingTensorAwareBooleanAlgebra and(OptionRegistry<ManipulationOption> registry) {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, LogicalTensorOperationsRepository.and(registry));
    }

    public OngoingTensorAwareBooleanAlgebra or() {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, LogicalTensorOperationsRepository.and(defaultRegistry()));
    }

    public OngoingTensorAwareBooleanAlgebra xor() {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, LogicalTensorOperationsRepository.and(defaultRegistry()));
    }

    public OngoingTensorAwareBooleanAlgebra nand() {
        return new OngoingTensorAwareBooleanAlgebra(this.tensor, LogicalTensorOperationsRepository.and(defaultRegistry()));
    }

    public Tensor<Boolean> and(Tensor<Boolean> tensorRight) {
        return LogicalTensorOperationsRepository.and(defaultRegistry()).perform(tensor, tensorRight);
    }

    public Tensor<Boolean> and(Tensor<Boolean> tensorRight, OptionRegistry<ManipulationOption> optionRegistry) {
        return LogicalTensorOperationsRepository.and(optionRegistry).perform(tensor, tensorRight);
    }

}
