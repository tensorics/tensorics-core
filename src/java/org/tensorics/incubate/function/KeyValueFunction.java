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

package org.tensorics.incubate.function;

/**
 * Represents the most general function that basically maps x-values to y
 * values. One could also see it as a function with exactly ONE variable (x).
 * The exact behavior of the {@link #getY(Comparable)} method depends on the
 * implementations.
 * 
 * @author agorzaws
 * @param <X>
 *            type of arguments
 * @param <Y>
 *            type of values
 */
public interface KeyValueFunction<X extends Comparable<? super X>, Y> {

	/**
	 * @param xValue
	 *            argument of type {@code <X>}
	 * @return value of type {@code <Y>}
	 */
	Y getY(X xValue);
}
