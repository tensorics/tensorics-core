package org.tensorics.core.tensor.dimtyped;

public interface Tensorbacked1D<C1, V> extends DimtypedTensorbacked<V> {

    V get(C1 c1);

}
