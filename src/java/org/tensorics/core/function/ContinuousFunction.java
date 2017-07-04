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
 * This math function can return x values for (almost) any X values. The exact
 * allowed x range depends on the implementation.
 * 
 * @author kfuchsbe
 * @param <X>
 *            the type of the values in x-direction (Independent variable)
 * @param <Y>
 *            the type of the values in y-direction (Dependent variable)
 */
public interface ContinuousFunction<X, Y> extends MathFunction<X, Y> {

	// as an idea
	// Range<X> allowedXRange();

}
