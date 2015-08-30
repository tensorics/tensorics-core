package org.tensorics.core.iterable.lang;

import java.util.ArrayList;
import java.util.List;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

public class OngoingQuantityIterableValueExtraction<V> {

    private final QuantityOperationRepository<V> operationRepository;
    private final Iterable<QuantifiedValue<V>> quantities;

    public OngoingQuantityIterableValueExtraction(Iterable<QuantifiedValue<V>> quantities,
            QuantityOperationRepository<V> operationRepository) {
        this.quantities = quantities;
        this.operationRepository = operationRepository;
    }

    public List<V> inUnitsOf(Unit unit) {
        List<V> results = new ArrayList<>();
        for (QuantifiedValue<V> quantity : quantities) {
            results.add(operationRepository.environment().quantification().convertValueToUnit(quantity, unit).value());
        }
        return results;
    }

    public List<V> inUnitsOf(javax.measure.unit.Unit<?> unit) {
        return inUnitsOf(JScienceUnit.of(unit));
    }

}
