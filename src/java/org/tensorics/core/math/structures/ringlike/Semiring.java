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

package org.tensorics.core.math.structures.ringlike;

import org.tensorics.core.math.structures.grouplike.CommutativeMonoid;
import org.tensorics.core.math.structures.grouplike.Monoid;

/**
 * The algebraic structure of a semiring, which has the following properties:
 * <p>
 * R is the underlying set; a, b, c are elements of R.
 * <ol>
 * <li>(R, +) is a commutative monoid with identity element 0:
 * <p>
 * (a + b) + c = a + (b + c)
 * <p>
 * 0 + a = a + 0 = a
 * <p>
 * a + b = b + a
 * <p>
 * <li>(R, *) is a monoid with identity element 1:
 * <p>
 * (a*b)*c = a*(b*c)
 * <p>
 * 1*a = a*1 = a
 * <p>
 * <li>Multiplication left and right distributes over addition:
 * <p>
 * a*(b + c) = (a*b) + (a*c) = (a + b)*c = (a*c) + (b*c)
 * <li>Multiplication by 0 annihilates R:
 * <p>
 * </ol>
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the structure
 */
public interface Semiring<T> extends RinglikeStructure<T> {

    @Override
    CommutativeMonoid<T> additionStructure();

    @Override
    Monoid<T> multiplicationStructure();

}
