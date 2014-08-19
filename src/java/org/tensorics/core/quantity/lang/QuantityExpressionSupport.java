/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.lang;

import org.tensorics.core.expressions.UnaryOperationExpression;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Provide tensoric eDSL elements which serve as starting point for descriptions of expressions of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class QuantityExpressionSupport<V> {

    private final QuantityOperationRepository<V> pseudoField;

    protected QuantityExpressionSupport(QuantityEnvironment<V> environment) {
        this.pseudoField = new QuantityOperationRepository<>(environment);
    }

    public Expression<QuantifiedValue<V>> valueOf(V value, Unit unit) {
        return ResolvedExpression.of((QuantifiedValue<V>) ImmutableQuantifiedValue.of(value, unit));
    }

    public Expression<QuantifiedValue<V>> valueOf(V value, javax.measure.unit.Unit<?> unit) {
        return valueOf(value, JScienceUnit.of(unit));
    }

    public OngoingDeferredQuantifiedScalarOperation<V> calculate(V value, javax.measure.unit.Unit<?> unit) {
        return calculate(valueOf(value, unit));
    }

    public OngoingDeferredQuantifiedScalarOperation<V> calculate(Expression<QuantifiedValue<V>> scalar) {
        return new OngoingDeferredQuantifiedScalarOperation<>(scalar, pseudoField);
    }

    public Expression<QuantifiedValue<V>> negativeOf(QuantifiedValue<V> element) {
        return negativeOf(ResolvedExpression.of(element));
    }

    public Expression<QuantifiedValue<V>> negativeOf(Expression<QuantifiedValue<V>> element) {
        return new UnaryOperationExpression<>(pseudoField.additiveInversion(), element);
    }

    public Expression<QuantifiedValue<V>> inverseOf(QuantifiedValue<V> element) {
        return inverseOf(ResolvedExpression.of(element));
    }

    public Expression<QuantifiedValue<V>> inverseOf(Expression<QuantifiedValue<V>> element) {
        return new UnaryOperationExpression<>(pseudoField.multiplicativeInversion(), element);
    }

    public QuantifiedValue<V> one() {
        return pseudoField.one();
    }

    public QuantifiedValue<V> zero() {
        return pseudoField.zero();
    }

    public QuantifiedValue<V> two() {
        return pseudoField.two();
    }

}
