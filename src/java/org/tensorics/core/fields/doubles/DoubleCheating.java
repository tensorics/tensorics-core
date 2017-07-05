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

package org.tensorics.core.fields.doubles;

import static com.google.common.base.Preconditions.checkNotNull;

import org.tensorics.core.math.Cheating;

/**
 * The implementation of those special methods which are (still) necessary to convert field elements to doubles and vice
 * versa. Anyhow, the implementation for doubles it is trivial and does not harm.
 * 
 * @author kfuchsbe
 */
public class DoubleCheating implements Cheating<Double> {

    /**
     * Converts a double value to a field element (double).
     * 
     * @deprecated the whole class should not be necessary in the future, if the unit-conversions are based fully on
     *             field operations.
     */
    @Deprecated
    @Override
    public Double fromDouble(double value) {
        return value;
    }

    /**
     * Converts field element (double) to a double value.
     * 
     * @deprecated the whole class should not be necessary in the future, if the unit-conversions are based fully on
     *             field operations.
     */
    @Deprecated
    @Override
    public double toDouble(Double value) {
        return checkNotNull(value, "Value must not be null.");
    }

}
