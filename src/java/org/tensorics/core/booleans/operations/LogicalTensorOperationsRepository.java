/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

/**
 * Reposirtory for the <b>logical operations</b> for Tensor<>: <lu>
 * <li>AND {@link #and()}</li>
 * <li>AND {@link #and(OptionRegistry)}</li>
 * <li>AND {@link #nand()}</li>
 * <li>AND {@link #nand(OptionRegistry)}</li>
 * <li>OR {@link #or()}</li>
 * <li>OR {@link #or(OptionRegistry)}</li>
 * <li>XOR {@link #xor()}</li>
 * <li>XOR {@link #xor(OptionRegistry)}</li> </lu>
 * <p>
 * For {@link OptionRegistry} that supports the operations. <lu>
 * <li>{@link #defaultRegistry()} returns default options registry that contains the following Options:
 * {@link ExactShapesOrOneZeroStrategy}, {@link LeftContextPreservedStrategy},
 * {@link BroadcastMissingDimensionsStrategy}</li>
 * <li>{@link #defaultRegistry(List)} returns the {@link #defaultRegistry()} with overriden options form the provided
 * list</li> </lu>
 * 
 * @author agorzaws
 */
public final class LogicalTensorOperationsRepository {

    private static final ImmutableList<ManipulationOption> DEFAULT_SET = ImmutableList.of(
            new LeftContextPreservedStrategy(), new ExactShapesOrOneZeroStrategy(),
            new BroadcastMissingDimensionsStrategy());

    private static OptionRegistry<ManipulationOption> registry(Collection<ManipulationOption> options) {
        return ImmutableOptionRegistry.of(options);
    }

    public static OptionRegistry<ManipulationOption> defaultRegistry(List<ManipulationOption> singletonList) {
        Set<ManipulationOption> combinedOverridenOpionSet = new HashSet<>();
        for (ManipulationOption oneDefaultOption : DEFAULT_SET) {
            for (ManipulationOption oneAdditionalOption : singletonList) {
                Class<? extends ManipulationOption> markerInterface = oneAdditionalOption.getMarkerInterface();
                if (markerInterface.isInstance(oneDefaultOption)) {
                    combinedOverridenOpionSet.add(oneAdditionalOption);
                    break;
                }
            }
            combinedOverridenOpionSet.add(oneDefaultOption);
        }
        return registry(combinedOverridenOpionSet);
    }

    public static OptionRegistry<ManipulationOption> defaultRegistry() {
        return registry(DEFAULT_SET);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> and(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsRepository.and(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> and() {
        return and(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> nand(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsRepository.nand(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> nand() {
        return nand(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> or(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsRepository.or(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> or() {
        return or(defaultRegistry());
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> xor(OptionRegistry<ManipulationOption> registry) {
        return new ElementBinaryFunction<>(LogicalOperationsRepository.xor(), registry);
    }

    public static BinaryFunction<Tensor<Boolean>, Tensor<Boolean>> xor() {
        return xor(defaultRegistry());
    }

}
