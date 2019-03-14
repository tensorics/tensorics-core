package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedBuilder1D<C1, V> extends DimtypedTensorbackedBuilder<V, Tensorbacked1D<C1, V>, TensorbackedBuilder1D<C1, V>> {

    TensorbackedBuilder1D<C1, V> put(C1 c1, V v);

}
