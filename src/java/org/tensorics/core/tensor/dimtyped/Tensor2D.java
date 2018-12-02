package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Tensor;

public interface Tensor2D<C1, C2, V> extends DimtypedTensor<V> {

    V get(C1 c1, C2 c2);

}
