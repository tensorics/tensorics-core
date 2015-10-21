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

import java.io.Serializable;
import java.util.List;

/**
 * THe following conditions are guaranteed:
 * <ul>
 * <li>The x-values are ordered by their natural ordering
 * <li>the lists returned by {@link #getXs()} and {@link #getYs()} are of the same length.
 * <ul>
 * <p>
 * 
 * @author agorzaws
 * @param <X> the type of the independent variable of the function
 * @param <Y> the type of the dependent variable of the function
 */
public interface DiscreteFunction<X extends Comparable<? super X>, Y> extends KeyValueFunction<X, Y>, Serializable {

    String getName();

    List<X> getXs();

    List<Y> getYs();

    List<Y> getYsErr();
}
