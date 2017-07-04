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

import com.google.common.base.Optional;

/**
 * Instances of this class define, how the errors have to be propagated in
 * different situations.
 * <p>
 * Currently, this is a simplification. For different assumtions (e.g.
 * uncorrelated errors), the error for product and division will not be the
 * same. Therefore, this for sure will require some refactorings. (An
 * abstraction to Field operations might have to be considered)
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the values
 */
public interface ErrorPropagationStrategy<V> extends ManipulationOption {

	Optional<V> errorForSumAndDifference(ErronousValue<V> left, ErronousValue<V> right);

	Optional<V> errorForProductAndDivision(ErronousValue<V> left, ErronousValue<V> right);

}
