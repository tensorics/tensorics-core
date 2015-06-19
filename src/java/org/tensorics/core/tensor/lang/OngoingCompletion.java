/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensor.Entry;

import com.google.common.base.Preconditions;

public class OngoingCompletion<S> {

    private final Tensor<S> tensor;

    OngoingCompletion(Tensor<S> tensor) {
        this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
    }

    public Tensor<S> with(Tensor<S> second) {
        checkNotNull(second, "second tensor must not be null");
        checkArgument(second.shape().dimensionSet().equals(dimensions()),
                "Tensors do not have the same dimensions! Completion not supported!");
        Builder<S> builder = ImmutableTensor.builder(dimensions());
        for (Entry<S> entry : second.entrySet()) {
            Position position = entry.getPosition();
            if (tensor.shape().contains(position)) {
                builder.putAt(tensor.get(position), position);
            } else {
                builder.put(entry);
            }
        }
        return builder.build();
    }

    private Set<Class<?>> dimensions() {
        return tensor.shape().dimensionSet();
    }

}
