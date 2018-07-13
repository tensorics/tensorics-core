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

package org.tensorics.core.quantity.operations;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A base class for unary operations on physical quantities.
 *
 * @author kfuchsbe
 * @param <S> the type of the scalar values (field elements) on which all the operations are based on
 */
public abstract class QuantityUnaryOperation<S> extends AbstractQuantityOperation<S>
        implements UnaryOperation<QuantifiedValue<S>> {
    private static final long serialVersionUID = 1L;

    protected QuantityUnaryOperation(QuantityEnvironment<S> environment) {
        super(environment);
    }

}
