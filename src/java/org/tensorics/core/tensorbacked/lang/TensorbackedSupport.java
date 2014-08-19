/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.operations.ElementTensorBackedUnaryOperation;

/**
 * Part of the tensorics fluent API that provides starting point methods for eDSL clauses that deal with calculations
 * and manipulations of tensor backed objects.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the field on which all the operations are based on)
 */
public class TensorbackedSupport<S> {
    private final Environment<S> environment;

    public TensorbackedSupport(Environment<S> environment) {
        this.environment = environment;
    }

    /**
     * Constructs a tensor backed objet of the same type as the input parameter, containing the negative values of the
     * original one.
     * 
     * @param tensorBacked to use
     * @return a tensor backed object of the same type, containing the negative values of the input object
     */
    public <TB extends Tensorbacked<S>> TB negativeOf(TB tensorBacked) {
        return new ElementTensorBackedUnaryOperation<S, TB>(environment.field().additiveInversion())
                .perform(tensorBacked);
    }

    /**
     * Allows to perform calculation on given tensor backed.
     * 
     * @param left to calculate with.
     * @return expression to calculate.
     */
    public final <TB extends Tensorbacked<S>> OngoingTensorBackedOperation<TB, S> calculate(TB left) {
        return new OngoingTensorBackedOperation<>(environment, left);
    }
}
