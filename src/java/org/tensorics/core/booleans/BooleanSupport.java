package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingIterableBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingScalarBooleanAlgebra;
import org.tensorics.core.booleans.lang.OngoingTensorBooleanAlgebra;
import org.tensorics.core.tensor.Tensor;

public class BooleanSupport {

	private final ScalarBooleanSupport scalarBooleanSupport = new ScalarBooleanSupport();
	private final IterableBooleanSupport iterableBooleanSupport = new IterableBooleanSupport();
	private final TensorBooleanSupport tensorBooleanSupport = new TensorBooleanSupport();

	public OngoingScalarBooleanAlgebra on(Boolean bool) {
		return scalarBooleanSupport.on(bool);
	}

	public OngoingTensorBooleanAlgebra on(Tensor<Boolean> tensor) {
		return tensorBooleanSupport.on(tensor);
	}

	public OngoingIterableBooleanAlgebra on(Iterable<Boolean> iterable) {
		return iterableBooleanSupport.on(iterable);
	}

}
