package org.tensorics.core.tensor.dimtyped;

public interface TensorBuilder3D<C1, C2, C3, V> extends DimtypedTensorBuilder<V, Tensor3D<C1, C2, C3, V>, TensorBuilder3D<C1, C2, C3, V>> {

    TensorBuilder3D<C1, C2, C3, V> put(C1 c1, C2 c2, C3 c3, V v);

}
