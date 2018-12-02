package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

import java.util.Map;

public interface DimtypedTensorBuilder<V, T extends DimtypedTensor<V>, B extends DimtypedTensorBuilder<V, T, B>> extends SpecificTensorBuilder<V, B> {

    @Override
    T build();
}
