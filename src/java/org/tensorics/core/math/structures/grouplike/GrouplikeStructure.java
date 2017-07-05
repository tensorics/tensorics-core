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

import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.Structure;

/**
 * Marks a algebraic structure that is of group-like type, aka it has one operation.
 * 
 * @author kfuchsbe
 * @param <T> the type of the element of the underlying set.
 */
public interface GrouplikeStructure<T> extends Structure<T> {

    /**
     * Has to return the binary operation (M x M = M) for this Magma.
     * 
     * @return the binary operation.
     */
    BinaryOperation<T> operation();

}
