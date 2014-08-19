/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.scalar.lang;

import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Provides starting methods for tensoric eDSL expressions, which create expressions of scalars.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the field on which all the operations are based on)
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

}
