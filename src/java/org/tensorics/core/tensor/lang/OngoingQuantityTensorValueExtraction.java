/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;
import static org.tensorics.core.tensor.stream.TensorStreamMappers.values;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.operations.QuantityOperationRepository;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.stream.TensorStreams;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

public class OngoingQuantityTensorValueExtraction<V> {

    private final QuantityOperationRepository<V> operationRepository;
    private final Tensor<QuantifiedValue<V>> quantifiedValueTensor;

    OngoingQuantityTensorValueExtraction(QuantityOperationRepository<V> operationRepository,
            Tensor<QuantifiedValue<V>> quantifiedValueTensor) {
        this.operationRepository = requireNonNull(operationRepository, "operationRepository must not be null");
        this.quantifiedValueTensor = requireNonNull(quantifiedValueTensor, "quantifiedValueTensor must not be null");
    }

    public Tensor<V> inUnitsOf(Unit unit) {
        return Tensorics.stream(quantifiedValueTensor).map(values(v -> valueInUnit(v, unit)))
                .collect(TensorStreams.toTensor(quantifiedValueTensor.shape().dimensionSet()));
    }

    public Tensor<V> inUnitsOf(javax.measure.unit.Unit<?> unit) {
        return inUnitsOf(JScienceUnit.of(unit));
    }

    private V valueInUnit(QuantifiedValue<V> v, Unit unit) {
        return operationRepository.environment().quantification().convertValueToUnit(v, unit).value();
    }

}
