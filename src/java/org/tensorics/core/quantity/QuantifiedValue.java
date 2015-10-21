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

package org.tensorics.core.quantity;

import java.io.Serializable;

/**
 * A scalar-type value representing a physical quantity. It contains:
 * <ul>
 * <li>the value itself
 * <li>the error of the value (optional)
 * <li>a validity flag
 * <li>the unit of the quantity
 * </ul>
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements) on which the quantity shall be based on
 */
public interface QuantifiedValue<S> extends Quantified, ValidityAware, ErronousValue<S>, Serializable {
    /*
     * NOTE: The logical name for this class would most probably be 'Quantity'. However, the intend to not use this name
     * at the present time is, to avoid name clashes with the jscience 'Quantity'.
     */
    /* Nothing special yet */
}
