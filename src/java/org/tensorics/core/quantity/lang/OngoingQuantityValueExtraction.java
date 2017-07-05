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
