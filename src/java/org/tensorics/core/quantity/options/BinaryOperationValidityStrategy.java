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
import org.tensorics.core.quantity.ValidityAware;

/**
 * Instances of this interface define, how the validity values for binary
 * operations of two validity aware values are propageted.
 * 
 * @author kfuchsbe
 */
public interface BinaryOperationValidityStrategy extends ManipulationOption {

	/**
	 * Has to return the resulting validity for the combination of the two
	 * scalars.
	 * 
	 * @param left
	 *            the left operand of a binary operation
	 * @param right
	 *            the right operand of a binary operation
	 * @return the combined validity
	 * @throws RuntimeException
	 *             if the combination of the two is not allowed
	 */
	boolean validityForBinaryOperation(ValidityAware left, ValidityAware right);

}
