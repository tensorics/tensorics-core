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

package org.tensorics.core.quantity.lang;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.conditions.QuantityPedicateRepository;
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
    private final QuantityPedicateRepository<V> predicateRepository;

    protected QuantitySupport(QuantityEnvironment<V> environment) {
        this.operationRepository = new QuantityOperationRepository<>(environment);
        this.predicateRepository = new QuantityPedicateRepository<>(environment);
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

    public QuantifiedValue<V> absoluteValueOf(QuantifiedValue<V> element) {
        return operationRepository.absoluteValue().perform(element);
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

    public OngoingQuantifiedScalarConversion<V> convert(QuantifiedValue<V> value) {
        return new OngoingQuantifiedScalarConversion<>(value, operationRepository.environment().quantification());
    }

    public OngoingQuantityValueExtraction<V> valueOf(QuantifiedValue<V> quantity) {
        return new OngoingQuantityValueExtraction<>(quantity, operationRepository);
    }

    protected QuantityOperationRepository<V> operationRepository() {
        return operationRepository;
    }

    protected QuantityPedicateRepository<V> predicateRepository() {
        return predicateRepository;
    }

    public OngoingQuantifiedScalarBinaryPredicate<V> testIf(QuantifiedValue<V> left) {
        return new OngoingQuantifiedScalarBinaryPredicate<>(predicateRepository, left);
    }
}
