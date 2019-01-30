package org.tensorics.core.tensor.dimtyped;

public interface TensorBuilder2D<C1, C2, V> extends DimtypedTensorBuilder<V, Tensor2D<C1, C2, V>, TensorBuilder2D<C1, C2, V>> {

    TensorBuilder2D<C1, C2, V> put(C1 c1, C2 c2, V v);

}
