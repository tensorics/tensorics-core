package org.tensorics.core.quantity.lang;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

public class OngoingQuantityValueExtraction<V> {

    private final QuantityOperationRepository<V> operationRepository;
    private final QuantifiedValue<V> quantity;

    public OngoingQuantityValueExtraction(QuantifiedValue<V> quantity,
            QuantityOperationRepository<V> operationRepository) {
        this.quantity = quantity;
        this.operationRepository = operationRepository;
    }

    public V inUnitsOf(Unit unit) {
        return operationRepository.environment().quantification().convertValueToUnit(quantity, unit).value();
    }

    public V inUnitsOf(javax.measure.unit.Unit<?> unit) {
        return inUnitsOf(JScienceUnit.of(unit));
    }

}
