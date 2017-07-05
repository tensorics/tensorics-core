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
 * A function for which the output can be retrieved for any input value.
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable of the function (input)
 * @param <Y> the type of the dependent variable of the function (output)
 */
public interface ContinuousFunction<X extends Comparable<? super X>, Y> extends KeyValueFunction<X, Y> {

    /* only marker at the moment */

}
