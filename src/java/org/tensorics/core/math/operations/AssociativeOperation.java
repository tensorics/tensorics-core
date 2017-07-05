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

package org.tensorics.core.math.operations;

/**
 * Represents a binary operation which guarantees that it is associative. In other words:
 * <p>
 * Let a,b and c being elements of an mathematical structure, 'o' representing the operation. Then the following is
 * valid: (a o b) o c = a o (b o c).
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements to which this operation can be applied.
 * @see <a href= "http://en.wikipedia.org/wiki/Associative">http://en.wikipedia.org/wiki/Associative</a>
 */
public interface AssociativeOperation<T> extends BinaryOperation<T> {
    /* Only a marker */
}
