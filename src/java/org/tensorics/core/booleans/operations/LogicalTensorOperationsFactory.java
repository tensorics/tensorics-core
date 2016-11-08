/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import java.util.Collection;

import org.tensorics.core.commons.options.ImmutableOptionRegistry;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryFunction;
import org.tensorics.core.tensor.options.BroadcastMissingDimensionsStrategy;
import org.tensorics.core.tensor.options.ExactShapesOrOneZeroStrategy;
import org.tensorics.core.tensor.options.LeftContextPreservedStrategy;

import com.google.common.collect.ImmutableList;

public final class LogicalTensorOperationsFactory {

    public static OptionRegistry<ManipulationOption> registry(Collection<ManipulationOption> options) {
        return ImmutableOptionRegistry.of(options);
    }

    public static OptionRegistry<ManipulationOption> defaultRegistry() {
        return registry(ImmutableList.of(new LeftContextPreservedStrategy(), new ExactShapesOrOneZeroStrategy(),
                new BroadcastMissingDimensionsStrategy()));
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> and(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsFactory.and(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> and() {
        return and(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> nand(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsFactory.nand(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> nand() {
        return nand(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> or(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsFactory.or(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> or() {
        return or(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> xor(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsFactory.xor(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> xor() {
        return xor(defaultRegistry());
    }
}
