/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import java.util.Objects;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryFunction;

/**
 * Repository for the logical operations of Tensors.
 * 
 * @author agorzaws
 */
public final class BooleanTensorOperationsRepository {

    private final OptionRegistry<ManipulationOption> registry;

    public BooleanTensorOperationsRepository(OptionRegistry<ManipulationOption> registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    public BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> and() {
        return new ElementBinaryFunction<>(BooleanOperations.and(), registry);
    }

    public BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> nand() {
        return new ElementBinaryFunction<>(BooleanOperations.nand(), registry);
    }

    public BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> or() {
        return new ElementBinaryFunction<>(BooleanOperations.or(), registry);
    }

    public BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> xor() {
        return new ElementBinaryFunction<>(BooleanOperations.xor(), registry);
    }

}
