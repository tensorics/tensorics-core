package org.tensorics.core.tensor.dimtyped;

public interface TensorBuilder4D<C1, C2, C3, C4, V> extends DimtypedTensorBuilder<V, Tensor4D<C1, C2, C3, C4, V>, TensorBuilder4D<C1, C2, C3, C4, V>> {

    TensorBuilder4D<C1, C2, C3, C4, V> put(C1 c1, C2 c2, C3 c3, C4 c4, V v);

}
