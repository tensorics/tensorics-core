package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

import com.google.common.collect.Range;

public class OngoingTensorbackedFiltering<E, TB extends Tensorbacked<E>> {

    private final TB tensorbacked;

    public OngoingTensorbackedFiltering(TB tensorbacked) {
        super();
        this.tensorbacked = tensorbacked;
    }

    public <C extends Comparable<C>> TB by(Class<C> coordinateClass, Range<C> coordinateRange) {
        return (TB) TensorbackedInternals.createBackedByTensor(tensorbacked.getClass(),
                TensorStructurals.filter(tensorbacked.tensor()).by(coordinateClass, coordinateRange));
    }

}
