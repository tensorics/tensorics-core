/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.Map;

import org.tensorics.core.iterable.lang.ScalarIterableSupport;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.Position;

/**
 * Performs an rms calculation over all in one direction of a tensor, if used for tensor reduction.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based on.
 */
public class RootMeanSquare<S> extends ScalarIterableSupport<S> implements ReductionStrategy<Object, S> {

    public RootMeanSquare(ExtendedField<S> field) {
        super(field);
    }

    @Override
    public S reduce(Map<? extends Object, S> inputValues) {
        return rmsOf(inputValues.values());
    }

    @Override
    public Position context(Position originalContext) {
        return originalContext;
    }

}
