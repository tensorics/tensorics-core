package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked3D<C1, C2, C3, V> extends DimtypedTensorbacked<V> {

    V get(C1 c1, C2 c2, C3 c3);

}
