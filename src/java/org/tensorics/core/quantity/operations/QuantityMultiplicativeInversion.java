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

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.quantity.options.QuantityEnvironment;
import org.tensorics.core.units.Unit;

/**
 * An unary operation describing the multiplicative inversion of a physical quantity (aka '1/x').
 *
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which all the operations are based
 */
public class QuantityMultiplicativeInversion<S> extends QuantityUnaryOperation<S> {
    private static final long serialVersionUID = 1L;

    public QuantityMultiplicativeInversion(QuantityEnvironment<S> environment) {
        super(environment);
    }

    @Override
    public QuantifiedValue<S> perform(QuantifiedValue<S> scalar) {
        S newValue = environment().field().multiplicativeInversion().perform(scalar.value());
        QuantificationStrategy<S> quant = environment().quantification();
        Unit newUnit = quant.divide(quant.one(), scalar.unit());
        /* XXX is the error propagated correctly here? */
        return Tensorics.quantityOf(newValue, newUnit).withError(scalar.error()).withValidity(scalar.validity());
    }

}
