package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked1dBuilder<C1, V, TB extends Tensorbacked1d<C1, V>> extends DimtypedTensorbackedBuilder<V, TB, Tensorbacked1dBuilder<C1, V, TB>> {

    Tensorbacked1dBuilder<C1, V, TB> put(C1 c1, V v);

}
