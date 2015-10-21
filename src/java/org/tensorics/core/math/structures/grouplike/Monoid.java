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
 * Represents the algebraic structure of a Monoid, which extends a semigroup by having an identity element.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the set on which the monoid is based
 * @see <a href="http://en.wikipedia.org/wiki/Monoid">http://en.wikipedia.org/wiki/Monoid</a>
 */
public interface Monoid<T> extends Semigroup<T> {

    /**
     * Has to return the element of the set, which represents the identity of the operation of the monoid.
     * 
     * @return the identity element of the monoid.
     */
    T identity();
}
