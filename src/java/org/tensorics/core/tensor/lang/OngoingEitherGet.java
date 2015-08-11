package org.tensorics.core.tensor.lang;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Preconditions;

public class OngoingEitherGet<S> {

    private final Tensor<S> tensor;
    private final S defaultValue;

    public OngoingEitherGet(Tensor<S> tensor, S defaultValue) {
        super();
        this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
        /* default value might be null! */
        this.defaultValue = defaultValue;
    }

    public S orGet(Object... coordinates) {
        return orGet(Position.of(coordinates));
    }

    public S orGet(Position position) {
        if (tensor.shape().contains(position)) {
            return tensor.get(position);
        } else {
            return defaultValue;
        }
    }

}
