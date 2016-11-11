package org.tensorics.core.booleans;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.lang.OngoingBooleanIterableOperation;
import org.tensorics.core.booleans.lang.OngoingBooleanScalarOperation;
import org.tensorics.core.booleans.lang.OngoingBooleanTensorOperation;
import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.tensor.Tensor;

public class BooleanSupport {

    private final OptionRegistry<ManipulationOption> optionRegistry;
    private final ScalarBooleanSupport scalarBooleanSupport;
    private final IterableBooleanSupport iterableBooleanSupport;
    private final TensorBooleanSupport tensorBooleanSupport;

    public BooleanSupport(OptionRegistry<ManipulationOption> optionRegistry) {
        this.optionRegistry = requireNonNull(optionRegistry, "optionRegistry must not be null");

        scalarBooleanSupport = new ScalarBooleanSupport();
        iterableBooleanSupport = new IterableBooleanSupport();
        tensorBooleanSupport = new TensorBooleanSupport(optionRegistry);
    }

    public OngoingBooleanScalarOperation calcLogical(Boolean left) {
        return scalarBooleanSupport.calcLogical(left);
    }

    public OngoingBooleanIterableOperation calcLogical(Iterable<Boolean> leftIterable) {
        return iterableBooleanSupport.calcLogical(leftIterable);
    }

    public OngoingBooleanTensorOperation calcLogical(Tensor<Boolean> leftTensor) {
        return tensorBooleanSupport.calcLogical(leftTensor);
    }

    public OngoingDetection detectWhere(Tensor<Boolean> tensor) {
        return tensorBooleanSupport.detectWhere(tensor);
    }

    public BooleanSupport with(ManipulationOption option) {
        requireNonNull(option, "option must not be null");
        return new BooleanSupport(optionRegistry.with(option));
    }

}
