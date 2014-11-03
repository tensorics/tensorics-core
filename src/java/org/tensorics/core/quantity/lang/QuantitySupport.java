/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.lang;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

/**
 * Provides starting methods for tensoric language expressions that operate on quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the field which is used for the operations)
 */
public class QuantitySupport<V> {

    private final QuantityOperationRepository<V> operationRepository;

    protected QuantitySupport(QuantityEnvironment<V> environment) {
        this.operationRepository = new QuantityOperationRepository<>(environment);
    }

    public QuantifiedValue<V> valueOf(V value, Unit unit) {
        return Tensorics.quantityOf(value, unit);
    }

    public QuantifiedValue<V> valueOf(V value, javax.measure.unit.Unit<?> unit) {
        return valueOf(value, JScienceUnit.of(unit));
    }

    public OngoingQuantifiedScalarOperation<V> calculate(V value, javax.measure.unit.Unit<?> unit) {
        return calculate(valueOf(value, unit));
    }

    public OngoingQuantifiedScalarOperation<V> calculate(QuantifiedValue<V> scalar) {
        return new OngoingQuantifiedScalarOperation<>(scalar, operationRepository);
    }

    public QuantifiedValue<V> negativeOf(QuantifiedValue<V> element) {
        return operationRepository.additiveInversion().perform(element);
    }

    public QuantifiedValue<V> inverseOf(QuantifiedValue<V> element) {
        return operationRepository.multiplicativeInversion().perform(element);
    }

    public QuantifiedValue<V> one() {
        return operationRepository.one();
    }

    public QuantifiedValue<V> zero() {
        return operationRepository.zero();
    }

    public QuantifiedValue<V> two() {
        return operationRepository.two();
    }

}
