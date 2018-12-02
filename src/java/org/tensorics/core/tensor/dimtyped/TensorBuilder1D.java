package org.tensorics.core.tensor.dimtyped;

public interface TensorBuilder1D<C1, V> extends DimtypedTensorBuilder<V, Tensor1D<C1, V>, TensorBuilder1D<C1, V>> {

    TensorBuilder1D<C1, V> put(C1 c1, V v);

}
