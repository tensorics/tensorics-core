package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedBuilder3D<C1, C2, C3, V> extends DimtypedTensorbackedBuilder<V, Tensorbacked3D<C1, C2, C3, V>, TensorbackedBuilder3D<C1, C2, C3, V>> {

    TensorbackedBuilder3D<C1, C2, C3, V> put(C1 c1, C2 c2, C3 c3, V v);

}
