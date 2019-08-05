package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked5dBuilder<C1, C2, C3, C4, C5, V, TB extends Tensorbacked5d<C1, C2, C3, C4, C5, V>> extends DimtypedTensorbackedBuilder<V, TB, Tensorbacked5dBuilder<C1, C2, C3, C4, C5, V, TB>> {

    Tensorbacked5dBuilder<C1, C2, C3, C4, C5, V, TB> put(C1 c1, C2 c2, C3 c3, C4 c4, C5 c5, V v);

}
