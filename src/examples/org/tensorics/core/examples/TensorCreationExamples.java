package org.tensorics.core.examples;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;

public class TensorCreationExamples {

    @SuppressWarnings("unused")
    public static void createZeroDimensionalOfString() {
        // tag::zerodimensionalString[]
        Tensor<String> user = Tensorics.scalarOf("user");
        // end::zerodimensionalString[]
    }

}
