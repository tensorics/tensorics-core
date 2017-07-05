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

public interface BinaryFunction<T, R> {

    /**
     * Has to be implemented to perform the actual operation. The order of operands might be important or not,
     * depending, if the operation is commutative or not.
     * 
     * @param left the left operand to be used in the operation
     * @param right the right operand to be used in the operation
     * @return the result of the operation
     */
    R perform(T left, T right);

}
