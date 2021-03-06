package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked4d<C1, C2, C3, C4, V> extends DimtypedTensorbacked<V> {

    default V get(C1 c1, C2 c2, C3 c3, C4 c4) {
        return tensor().get(c1, c2, c3, c4);
    }

    default boolean contains(C1 c1, C2 c2, C3 c3, C4 c4) {
        return tensor().contains(c1, c2, c3, c4);
    }

}
