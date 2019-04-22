package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked3d<C1, C2, C3, V> extends DimtypedTensorbacked<V> {

    default V get(C1 c1, C2 c2, C3 c3) {
        return tensor().get(c1, c2, c3);
    }

}
