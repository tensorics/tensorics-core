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

import org.tensorics.core.math.operations.UnaryOperation;

/**
 * Represents the algebraic structure of a group, which has with respect to a monoid the additional property that it
 * provides a unitary operation, which gives rise to inverse elements.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the group
 * @see <a href= "http://en.wikipedia.org/wiki/Group_(mathematics)">http://en.wikipedia.org/wiki/Group_(mathematics)</a>
 */
public interface Group<T> extends Monoid<T> {

    /**
     * Has to return the inversion operation for elements.
     * 
     * @return the inversion operation
     */
    UnaryOperation<T> inversion();
}
