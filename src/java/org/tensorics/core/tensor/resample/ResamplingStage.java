/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

public class ResamplingStage<V, C> implements Tensoric<V> {

    private final Tensoric<V> nonresampled;
    private final SingleDimensionResampler<C, V> resampler;

    public ResamplingStage(Tensor<V> nonresampled, SingleDimensionResampler<C, V> resampler) {
        this.nonresampled = nonresampled;
        this.resampler = resampler;
    }

    @Override
    public V get(Position position) {
        return null;
    }

    @Override
    public boolean contains(Position position) {
        // TODO Auto-generated method stub
        return false;
    }
}
