package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

import com.google.common.base.Preconditions;

public class OngoingTensorbackedConstruction<V, TB extends Tensorbacked<V>> {

    private final Class<TB> tensorbackedClass;

    public OngoingTensorbackedConstruction(Class<TB> tensorbackedClass) {
        super();
        this.tensorbackedClass = Preconditions.checkNotNull(tensorbackedClass, "tensorbackedClass must not be null");
    }

    public TB byMerging(Iterable<Tensor<V>> tensors) {
        return TensorbackedInternals.createBackedByTensor(tensorbackedClass, TensorStructurals.merge(tensors));
    }

    public TB byMergingTb(Iterable<? extends Tensorbacked<V>> tensorbackeds) {
        return byMerging(TensorbackedInternals.tensorsOf(tensorbackeds));
    }

}
