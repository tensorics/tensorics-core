package org.tensorics.core.examples;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;

public class TensorCreationExamples {

	public static void createZeroDimensionalOfString() {
// tag::zerodimensionalString[]
Tensor<String> user = ImmutableTensor.zeroDimensionalOf("user");
// end::zerodimensionalString[]
	}

}
