package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedBuilder4D<C1, C2, C3, C4, V> extends DimtypedTensorbackedBuilder<V, Tensorbacked4D<C1, C2, C3, C4, V>, TensorbackedBuilder4D<C1, C2, C3, C4, V>> {

    TensorbackedBuilder4D<C1, C2, C3, C4, V> put(C1 c1, C2 c2, C3 c3, C4 c4, V v);

}
