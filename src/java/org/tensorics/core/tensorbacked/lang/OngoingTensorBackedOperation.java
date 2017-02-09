// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.tensorbacked.lang;

import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

/**
 * Collects all fluent API elements for the {@link Tensorbacked} objects.
 * 
 * @author agorzaws
 * @param <TB>
 * @param <V>
 */
public class OngoingTensorBackedOperation<TB extends Tensorbacked<V>, V> implements OngoingOperation<TB, V> {

    private final Environment<V> environment;
    private final TB left;

    /**
     * @param environment the environment to use
     * @param left first {@link Tensorbacked} to use
     */
    public OngoingTensorBackedOperation(Environment<V> environment, TB left) {
        this.environment = environment;
        this.left = left;
    }

    /**
     * Allows to sum two {@link Tensorbacked}.
     * 
     * @param right as tensor to add
     * @return result of summing two tensors
     */

    @Override
    public TB plus(TB right) {
        return evaluate(right.tensor(), environment.field().addition());
    }

    @Override
    public TB minus(TB right) {
        return evaluate(right.tensor(), environment.field().subtraction());
    }

    /**
     * Constructs a tensor backed object, of the same type as the input objects, which will contain elements which are
     * the products of the respective elements of the input objects.
     * 
     * @param right second {@link Tensorbacked} to use
     * @return a tensor backed object of the same as the input object, containing the all the values of the left tensor,
     *         multiplied by the values of the right tensor
     */
    @Override
    public TB elementTimes(TB right) {
        return elementTimesT(right.tensor());
    }

    /**
     * Construct a tensor backed object of the same type of the left given object, multiplied all the elements with the
     * given tensor.
     * 
     * @param right the right tensor with which to multiyply all the elements
     * @return a new tensor backed object, containing all the elements muyltiplied by each other
     */
    private TB elementTimesT(Tensor<V> right) {
        return evaluate(right, environment.field().multiplication());
    }

    @Override
    public TB elementTimesV(V value) {
        return elementTimesT(Tensorics.zeroDimensionalOf(value));
    }

    @Override
    public TB elementDividedBy(TB right) {
        return elementDividedByT(right.tensor());
    }

    /**
     * Constructs a new tensor backed object of the same type as the left operand, containing elements divided by the
     * corresponding elements of the given tensor.
     * 
     * @param right the tensor with whose elements to divide the elements of the left operand
     * @return a new tensorbacked object containing the left elements divided by the right ones
     */
    private TB elementDividedByT(Tensor<V> right) {
        return evaluate(right, environment.field().division());
    }

    @Override
    public TB elementDividedByV(V value) {
        return elementDividedByT(Tensorics.zeroDimensionalOf(value));
    }

    private TB evaluate(Tensor<V> right, BinaryOperation<V> operation) {
        Tensor<V> result = new ElementBinaryOperation<V>(operation, environment.options()).perform(left.tensor(),
                right);
        return createBackedByTensor(TensorbackedInternals.classOf(left), result);
    }

}
