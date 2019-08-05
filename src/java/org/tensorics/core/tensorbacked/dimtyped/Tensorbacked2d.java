package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked2d<C1, C2, V> extends DimtypedTensorbacked<V> {

    default V get(C1 c1, C2 c2) {
        return tensor().get(c1, c2);
    }

}
