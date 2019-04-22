package org.tensorics.core.tensorbacked.dimtyped;

public interface TensorbackedScalarBuilder<V, TB extends TensorbackedScalar<V>> extends DimtypedTensorbackedBuilder<V, TB, TensorbackedScalarBuilder<V, TB>> {

    TensorbackedScalarBuilder<V, TB> put(V v);

}
