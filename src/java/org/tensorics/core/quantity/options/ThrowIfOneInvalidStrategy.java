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

import org.tensorics.core.quantity.ValidityAware;

/**
 * This strategy throws an exception, if at least one of the two operands is invalid.
 * 
 * @author kfuchsbe
 */
public class ThrowIfOneInvalidStrategy implements BinaryOperationValidityStrategy {

    @Override
    public boolean validityForBinaryOperation(ValidityAware left, ValidityAware right) {
        if (left.validity() && right.validity()) {
            return true;
        }
        throw new RuntimeException("One of the two operands is invalid!");
    }

    @Override
    public Class<BinaryOperationValidityStrategy> getMarkerInterface() {
        return BinaryOperationValidityStrategy.class;
    }

}
