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

package org.tensorics.core.iterable.lang;

import org.tensorics.core.quantity.lang.QuantityExpressionSupport;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * Provides methods, being part of the tensorics eDSL, which deal with expressions of iterables of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the elements of the field on which all the operations are based on.
 */
public class QuantityIterableExpressionSupport<V> extends QuantityExpressionSupport<V> {

    protected QuantityIterableExpressionSupport(QuantityEnvironment<V> environment) {
        super(environment);
    }

    /* Nothing implemented for the moment */
}
