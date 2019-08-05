package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedScalar<V> extends DimtypedTensorbacked<V> {

    default V get() {
        return tensor().get();
    }
    
    default V value() {
        return get();
    }
}
