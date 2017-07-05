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

package org.tensorics.core.math;

import org.tensorics.core.math.operations.BinaryOperation;

/**
 * Exposes possibilities for field calculations in more explicit ways. This can be used once for convenience reasons,
 * and on the other hand it allows to define the required behaviour for field based calculations in a less strict way,
 * which might be better for optimization purposes. E.g. inverse operations can be only created once and do not need to
 * be re-created every time they are required.
 * 
 * @author kfuchsbe
 * @param <T> the type of the field elements
 */
public interface ExtendedField<T> extends ExplicitField<T> {

    /**
     * Has to return the operation for a^b, for a being the left operator, b being the right one.
     * 
     * @return the operation for a^b.
     */
    BinaryOperation<T> power();

    BinaryOperation<T> root();

    /**
     * Returns an object that provides methods for cheating. The usage of these methods is highly discouraged for
     * clients, because they are planned to be removed, as soon as they are not required anymore by the framework. This
     * will be the case as soon as a unit-treating framework is in place, which will be based on fields only. This is
     * also the reason, why this method is marked as deprecated.
     * 
     * @return the object providing methods for cheating
     * @deprecated Will be removed, as soon as a unit-treatment framework, purely based on fields is in place.
     */
    @Deprecated
    Cheating<T> cheating();
}
