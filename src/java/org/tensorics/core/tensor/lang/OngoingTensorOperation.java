/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.iterable.operations.IterableSum;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensor.operations.InnerTensorOperation;

/**
 * Part of the fluent API for operations on tensors.
 * 
 * @author agorzaws, kfuchbe
 * @param <C>
 * @param <V>
 */
public class OngoingTensorOperation<C, V> implements OngoingOperation<Tensor<V>, V> {

    private final Environment<V> environment;
    private final Tensor<V> left;

    /**
     * @param environment the environment to use
     * @param left first tensoric to use
     */
    public OngoingTensorOperation(Environment<V> environment, Tensor<V> left) {
        this.environment = environment;
        this.left = left;
    }

    /**
     * Returns the sum of the left tensor with the given right tensor.
     * 
     * @param right as tensor to add
     * @return result of summing two tensors
     */
    @Override
    public Tensor<V> plus(Tensor<V> right) {
        return evaluate(right, environment.field().addition());
    }

    @Override
    public Tensor<V> minus(Tensor<V> right) {
        return evaluate(right, environment.field().subtraction());
    }

    /**
     * Calls multiplication operation on the elements of the two input tensors
     * 
     * @param right second tensoric to use
     * @return A tensor, containing elements that are the product of the respecive elemtents of the two input tensors
     */
    @Override
    public Tensor<V> elementTimes(Tensor<V> right) {
        return evaluate(right, environment.field().multiplication());
    }

    @Override
    public Tensor<V> elementDividedByV(V value) {
        return elementDividedBy(Tensorics.zeroDimensionalOf(value));
    }

    private Tensor<V> evaluate(Tensor<V> right, BinaryOperation<V> operation) {
        return new ElementBinaryOperation<V>(operation, environment.options()).perform(left, right);
    }

    @Override
    public Tensor<V> elementTimesV(V right) {
        return elementTimes(Tensorics.zeroDimensionalOf(right));
    }

    @Override
    public Tensor<V> elementDividedBy(Tensor<V> right) {
        return evaluate(right, environment.field().division());
    }

    /**
     * Performs the inner tensor product of the left tensor with the right tensor by taking care about co-and
     * contravariant coordinates and following the tensoric-equivalen of Einsteins sum convention.
     * 
     * @param right the right tensor to multiply onto the left
     * @return a tensor resulting from the multiplication
     * @see InnerTensorOperation
     */
    public Tensor<V> times(Tensor<V> right) {
        return new InnerTensorOperation<V>(environment.field().multiplication(),
                new IterableSum<>(environment.field()), environment.options()).perform(left, right);
    }

}
