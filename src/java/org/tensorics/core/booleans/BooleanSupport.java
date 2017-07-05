package org.tensorics.core.booleans;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.lang.OngoingBooleanIterableOperation;
import org.tensorics.core.booleans.lang.OngoingBooleanScalarOperation;
import org.tensorics.core.booleans.lang.OngoingBooleanTensorOperation;
import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.lang.ManipulationOptions;
import org.tensorics.core.tensor.Tensor;

/**
 * A support class that contains delegation to all possible operation types:
 * <li>{@link ScalarBooleanSupport}</li>
 * <li>{@link IterableBooleanSupport}</li>
 * <li>{@link TensorBooleanSupport} that contains information about possible {@link OptionRegistry}.</li>
 * 
 * @author agorzaws
 */
public class BooleanSupport {

    /**
     * Delegation to the {@link ManipulationOption} default structural method!
     */
    public static final OptionRegistry<ManipulationOption> DEFAULT_REGISTRY = ManipulationOptions
            .defaultStructuralOnly();

    private final OptionRegistry<ManipulationOption> optionRegistry;
    private final ScalarBooleanSupport scalarBooleanSupport;
    private final IterableBooleanSupport iterableBooleanSupport;
    private final TensorBooleanSupport tensorBooleanSupport;

    /**
     * Creates a support with default structural options
     */
    public BooleanSupport() {
        this(DEFAULT_REGISTRY);
    }

    /** Creates a support with provided structutal options */
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
