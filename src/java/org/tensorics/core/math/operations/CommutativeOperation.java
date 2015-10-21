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
 * Represents a binary operation on elements of a set, where the following condition is fullfilled:
 * <p>
 * a o b = b o a, with 'a', 'b' being elements of the underlying set, 'o' being the operation.
 * 
 * @author kfuchsbe
 * @param <T>
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Commutative_property">http://en.wikipedia.org/wiki/Commutative_property</a>
 */
public interface CommutativeOperation<T> extends BinaryOperation<T> {
    /* only a marker */
}
