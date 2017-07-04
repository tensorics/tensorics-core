// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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
package org.tensorics.core.function;

/**
 * Represents a math function that has been constructed by interpolating a
 * discrete function so that it can return Y values for any X values. The
 * accuracy of the y values obtained is not defined by this contract.
 * 
 * @author kfuchsbe, caguiler
 * @param <X>
 *            the type of the values in x-direction (Independent variable)
 * @param <Y>
 *            the type of the values in y-direction (Dependent variable)
 */
public interface InterpolatedFunction<X, Y> extends ContinuousFunction<X, Y>, DiscreteFunction<X, Y> {
	/* Only marker at this point */
}
