package org.tensorics.core.tensor.dimtyped;

public interface TensorbackedBuilder5D<C1, C2, C3, C4, C5, V> extends DimtypedTensorbackedBuilder<V, Tensorbacked5D<C1, C2, C3, C4, C5, V>, TensorbackedBuilder5D<C1, C2, C3, C4, C5, V>> {

    TensorbackedBuilder5D<C1, C2, C3, C4, C5, V> put(C1 c1, C2 c2, C3 c3, C4 c4, C5 c5, V v);

}
