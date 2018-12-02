package org.tensorics.core.tensor.dimtyped;

public interface TensorBuilder5D<C1, C2, C3, C4, C5, V> extends DimtypedTensorBuilder<V, Tensor5D<C1, C2, C3, C4, C5, V>, TensorBuilder5D<C1, C2, C3, C4, C5, V>> {

    TensorBuilder5D<C1, C2, C3, C4, C5, V> put(C1 c1, C2 c2, C3 c3, C4 c4, C5 c5, V v);

}
