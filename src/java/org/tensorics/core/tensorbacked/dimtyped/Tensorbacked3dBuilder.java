package org.tensorics.core.tensorbacked.dimtyped;

public interface Tensorbacked3dBuilder<C1, C2, C3, V, TB extends Tensorbacked3d<C1, C2, C3, V>> extends DimtypedTensorbackedBuilder<V, TB, Tensorbacked3dBuilder<C1, C2, C3, V, TB>> {

    Tensorbacked3dBuilder<C1, C2, C3, V, TB> put(C1 c1, C2 c2, C3 c3, V v);

}
