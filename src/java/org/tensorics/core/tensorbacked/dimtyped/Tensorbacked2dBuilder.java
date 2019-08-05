package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked2dBuilder<C1, C2, V, TB extends Tensorbacked2d<C1, C2, V>> extends DimtypedTensorbackedBuilder<V, TB, Tensorbacked2dBuilder<C1, C2, V, TB>> {

    Tensorbacked2dBuilder<C1, C2, V, TB> put(C1 c1, C2 c2, V v);

}
