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

package org.tensorics.core.math.structures;

/**
 * The base interface for all algebraic structures. A basic property of an mathematical structure is always a set of
 * elements. Since there can be infinite and finite structures, it does not always make sense (or is impossible) to
 * explicitly state the involved set.
 *
 * @author kfuchsbe
 * @param <T> the type of the elements of the mathematical structure
 * @see <a href= "http://en.wikipedia.org/wiki/Algebraic_structure">http://en.wikipedia.org/wiki/Algebraic_structure</a>
 */
public interface Structure<T> {
    /* Only marker interface */
}
