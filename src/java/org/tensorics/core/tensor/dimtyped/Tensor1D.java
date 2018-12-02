package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Tensor;

public interface Tensor1D<C1, V> extends DimtypedTensor<V> {

    V get(C1 c1);

}
