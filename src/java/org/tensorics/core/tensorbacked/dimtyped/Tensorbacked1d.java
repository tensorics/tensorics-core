package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked1d<C1, V> extends DimtypedTensorbacked<V> {

    default V get(C1 c1) {
        return tensor().get(c1);
    }
    
    default boolean contains(C1 c1) {
        return tensor().contains(c1);
    }

}
