package org.tensorics.core.tensor.dimtyped;

public interface Tensorbacked4D<C1, C2, C3, C4, V> extends DimtypedTensorbacked<V> {

    V get(C1 c1, C2 c2, C3 c3, C4 c4);

}
