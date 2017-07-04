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

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.quantity.Quantified;
import org.tensorics.core.units.Unit;

/**
 * Provides methods which allow to convert values of a certain type together with units.
 * 
 * @author kfuchsbe
 * @param <T>
 */
public interface QuantificationStrategy<T> extends ManipulationOption {

    <V extends ErronousValue<T> & Quantified> OperandPair<T, Unit> asSameUnit(V left, V right);

    Unit multiply(Unit left, Unit right);

    Unit divide(Unit left, Unit right);

    Unit root(Unit left, T right);

    Unit power(Unit unit, T value);

    Unit one();

    <V extends ErronousValue<T> & Quantified> ErronousValue<T> convertValueToUnit(V value, Unit unit);
}
