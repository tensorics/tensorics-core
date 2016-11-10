package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.booleans.lang.OngoingIterableBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingScalarBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingTensorBooleanAlgebra;
import org.tensorics.core.tensor.Tensor;

public class BooleanSupport {

    private final ScalarBooleanSupport scalarBooleanSupport = new ScalarBooleanSupport();
    private final IterableBooleanSupport iterableBooleanSupport = new IterableBooleanSupport();
    private final TensorBooleanSupport tensorBooleanSupport = new TensorBooleanSupport();

    public OngoingScalarBooleanAlgebra calcLogical(Boolean bool) {
        return scalarBooleanSupport.calcLogical(bool);
    }

    public OngoingIterableBooleanAlgebra calcLogical(Iterable<Boolean> iterable) {
        return iterableBooleanSupport.calcLogical(iterable);
    }

    public OngoingTensorBooleanAlgebra calcLogical(Tensor<Boolean> tensor) {
        return tensorBooleanSupport.calcLogical(tensor);
    }

    public OngoingDetection detect() {
        return tensorBooleanSupport.detect();
    }

}
