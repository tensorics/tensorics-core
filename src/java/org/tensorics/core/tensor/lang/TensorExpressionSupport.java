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

package org.tensorics.core.tensor.lang;

import static org.tensorics.core.tensor.expressions.TensorExpressions.creationOfShapeValue;
import static org.tensorics.core.tensor.expressions.TensorExpressions.elementwise;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.iterable.lang.ScalarIterableExpressionSupport;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tree.domain.Expression;

/**
 * Extends the usage of fields by operations defined on tensors
 * 
 * @author kfuchsbe, agorzaws
 * @param <V> the type of the elements of the tensor(ic)
 */
public class TensorExpressionSupport<V> extends ScalarIterableExpressionSupport<V> {

    private final ExtendedField<V> field;
    private final OptionRegistry<ManipulationOption> optionRegistry;

    public TensorExpressionSupport(Environment<V> environment) {
        super(environment.field());
        this.field = environment.field();
        this.optionRegistry = environment.options();
    }

    /**
     * Allows to perform calculation on given tensoric.
     * 
     * @param tensoric to calculate with.
     * @return expression to calculate.
     */
    public final OngoingDeferredTensorOperation<V> calculateT(Expression<Tensor<V>> tensoric) {
        return new OngoingDeferredTensorOperation<>(field, optionRegistry, tensoric);
    }

    /**
     * returns a ZERO value {@link Tensor} for given {@link Shape}.
     * 
     * @param shape to use.
     * @return a {@link Tensor} of given {@link Shape} filled with 0.0;
     */
    public final Expression<Tensor<V>> zeros(Shape shape) {
        return creationOfShapeValue(shape, field.zero());
    }

    /**
     * returns a IDENTITY value {@link Tensor} for given {@link Shape}.
     * 
     * @param shape to use.
     * @return a {@link Tensor} of given {@link Shape} filled with field identities;
     */
    public final Expression<Tensor<V>> ones(Shape shape) {
        return creationOfShapeValue(shape, field.one());
    }

    /**
     * @param tensor to use.
     * @return a {@link Tensor} with field inverse values
     */
    public final Expression<Tensor<V>> elementInverseOf(Expression<Tensor<V>> tensor) {
        return elementwise(field.multiplicativeInversion(), tensor);
    }

    /**
     * @param tensor to use
     * @return a {@link Tensor} of negative values
     */
    public final Expression<Tensor<V>> elementNegativeOf(Expression<Tensor<V>> tensor) {
        return elementwise(field.additiveInversion(), tensor);
    }

}
