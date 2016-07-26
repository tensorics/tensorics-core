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

import org.tensorics.core.math.Math;

/**
 * Provides mathematical operations on doubles. This is of course the most trivial implementation of the {@link Math}
 * interface, since it simply delegates to the {@link java.lang.Math} class. However, this is necessary to keep the the
 * tensorics language generic for any scalar values.
 * 
 * @author kfuchsbe
 */
public class DoubleMath implements Math<Double> {

    @Override
    public Double pow(Double base, Double exponent) {
        return java.lang.Math.pow(base, exponent);
    }

    @Override
    public Double root(Double base, Double exponent) {
        return java.lang.Math.pow(base, 1.0 / exponent);
    }
}
