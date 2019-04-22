package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked4dBuilder<C1, C2, C3, C4, V, TB extends Tensorbacked4d<C1, C2, C3, C4, V>> extends DimtypedTensorbackedBuilder<V, TB, Tensorbacked4dBuilder<C1, C2, C3, C4, V, TB>> {

    Tensorbacked4dBuilder<C1, C2, C3, C4, V, TB> put(C1 c1, C2 c2, C3 c3, C4 c4, V v);

}
