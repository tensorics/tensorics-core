package org.tensorics.core.tensor.dimtyped;

public interface Tensorbacked2D<C1, C2, V> extends DimtypedTensorbacked<V> {

    V get(C1 c1, C2 c2);

}
