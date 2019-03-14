package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedBuilder2D<C1, C2, V> extends DimtypedTensorbackedBuilder<V, Tensorbacked2D<C1, C2, V>, TensorbackedBuilder2D<C1, C2, V>> {

    TensorbackedBuilder2D<C1, C2, V> put(C1 c1, C2 c2, V v);

}
