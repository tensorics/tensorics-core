package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked5d<C1, C2, C3, C4, C5, V> extends DimtypedTensorbacked<V> {

    default V get(C1 c1, C2 c2, C3 c3, C4 c4, C5 c5) {
        return tensor().get(c1, c2, c3, c4, c5);
    }
    
    default boolean contains(C1 c1, C2 c2, C3 c3, C4 c4, C5 c5) {
        return tensor().contains(c1, c2, c3, c4, c5);
    }
    

}
