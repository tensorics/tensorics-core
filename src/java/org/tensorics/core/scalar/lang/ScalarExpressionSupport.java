// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.scalar.lang;

import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides starting methods for tensoric eDSL expressions, which create expressions of scalars.
 *
 * @param <V> the type of the scalar values (elements of the field on which all the operations are based on)
 * @author kfuchsbe
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ScalarExpressionSupport<V> {

    private final ExtendedField<V> extendedField;

    public ScalarExpressionSupport(ExtendedField<V> field) {
        this.extendedField = field;
    }

    public final Expression<V> negativeOf(V element) {
        return negativeOf(ResolvedExpression.of(element));
    }

    public final Expression<V> negativeOf(Expression<V> element) {
        return new UnaryOperationExpression<>(this.field().additiveInversion(), element);
    }

    public final Expression<V> inverseOf(V element) {
        return inverseOf(ResolvedExpression.of(element));
    }

    public final Expression<V> inverseOf(Expression<V> element) {
        return new UnaryOperationExpression<>(this.field().multiplicativeInversion(), element);
    }

    protected ExtendedField<V> field() {
        return this.extendedField;
    }

    public final V zero() {
        return this.field().zero();
    }

    public final V two() {
        return this.field().two();
    }

    public final V one() {
        return this.field().one();
    }

    public Expression<V> squareRootOf(V value) {
        return squareRootOf(ResolvedExpression.of(value));
    }

    public Expression<V> squareRootOf(Expression<V> value) {
        return calculate(value).root(ResolvedExpression.of(two()));
    }

    public Expression<V> squareOf(V value) {
        return squareOf(ResolvedExpression.of(value));
    }

    public Expression<V> squareOf(Expression<V> value) {
        return calculate(value).toThePowerOf(ResolvedExpression.of(two()));
    }

    public OngoingDeferredBinaryOperation<V> calculate(V left) {
        return calculate(ResolvedExpression.of(left));
    }

    public OngoingDeferredBinaryOperation<V> calculate(Expression<V> left) {
        return new OngoingDeferredBinaryOperation<>(extendedField, left);
    }

    public OngoingDeferredBinaryPredicate<V> testIf(Expression<V> expression) {
        return new OngoingDeferredBinaryPredicate<>(extendedField, expression);
    }
}
