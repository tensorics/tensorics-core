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

package org.tensorics.core.math.structures.grouplike;

/**
 * The algebraic structure of a Magma (or also called groupoid). It has the
 * following properties:
 * <ul>
 * <li>a set (M) of elements of type T</li>
 * <li>one binary operation M x M : M</li>
 * </ul>
 * 
 * @param <T>
 * @see <a href=
 *      "http://en.wikipedia.org/wiki/Magma_(algebra)">http://en.wikipedia.org/wiki/Magma_(algebra)</a>
 */
public interface Magma<T> extends GrouplikeStructure<T> {
	/* everything already defined in super interfaces */
}
