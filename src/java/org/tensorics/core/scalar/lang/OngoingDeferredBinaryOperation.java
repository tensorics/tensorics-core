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

package org.tensorics.core.scalar.lang;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides methods to describe the right hand part of a binary operations for scalar expressions.
 * <p>
 * This class is part of the tensorcs fluent API.
 *
 * @param <S> the type of the scalar values (elements of the field)
 * @author kfuchsbe
 */
public class OngoingDeferredBinaryOperation<S> {

    private final ExtendedField<S> field;
    private final Expression<S> left;

    public OngoingDeferredBinaryOperation(ExtendedField<S> field, Expression<S> left) {
        this.field = field;
        this.left = left;
    }

    public Expression<S> plus(S right) {
        return plus(ResolvedExpression.of(right));
    }

    public Expression<S> plus(Expression<S> right) {
        return new BinaryOperationExpression<>(field.addition(), left, right);
    }

    public Expression<S> minus(S right) {
        return minus(ResolvedExpression.of(right));
    }

    public Expression<S> minus(Expression<S> right) {
        return new BinaryOperationExpression<>(field.subtraction(), left, right);
    }

    public Expression<S> times(S right) {
        return times(ResolvedExpression.of(right));
    }

    public Expression<S> times(Expression<S> right) {
        return new BinaryOperationExpression<>(field.multiplication(), left, right);
    }

    public Expression<S> dividedBy(S right) {
        return dividedBy(ResolvedExpression.of(right));
    }

    public Expression<S> dividedBy(Expression<S> right) {
        return new BinaryOperationExpression<>(field.division(), left, right);
    }

    public Expression<S> toThePowerOf(S power) {
        return toThePowerOf(ResolvedExpression.of(power));
    }

    public Expression<S> toThePowerOf(Expression<S> power) {
        return new BinaryOperationExpression<>(field.power(), left, power);
    }

    public Expression<S> root(S root) {
        return root(ResolvedExpression.of(root));
    }

    public Expression<S> root(Expression<S> root) {
        return new BinaryOperationExpression<>(field.power(), left, inverseOf(root));
    }

    private UnaryOperationExpression<S> inverseOf(Expression<S> value) {
        return new UnaryOperationExpression<S>(field.multiplicativeInversion(), value);
    }

}