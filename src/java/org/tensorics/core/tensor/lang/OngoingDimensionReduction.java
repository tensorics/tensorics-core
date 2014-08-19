/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.reduction.Averaging;
import org.tensorics.core.reduction.RootMeanSquare;
import org.tensorics.core.tensor.Tensor;

/**
 * Part of the tensorics fluent API, that allows to further describe how a tensor dimesion shall be reduced (where the
 * field was not yet known in the previous expression part)
 * 
 * @author kfuchsbe
 * @param <C> the type of the dimension in which the tensor shall be reduced
 * @param <S> the type of the scalars (elements of the field on which all the operations are based on)
 */
public final class OngoingDimensionReduction<C, S> extends OngoingStructuralReduction<C, S> {

    public OngoingDimensionReduction(Tensor<S> tensor, Class<C> dimension) {
        super(tensor, dimension);
    }

    public Tensor<S> byAveragingIn(ExtendedField<S> field) {
        return reduceBy(new Averaging<>(field));
    }

    public Tensor<S> byRmsIn(ExtendedField<S> field) {
        return reduceBy(new RootMeanSquare<>(field));
    }
}