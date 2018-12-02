package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Tensor;

public interface Tensor3D<C1, C2, C3, V> extends DimtypedTensor<V> {

    V get(C1 c1, C2 c2, C3 c3);

}
