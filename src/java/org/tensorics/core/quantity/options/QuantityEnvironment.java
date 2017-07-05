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

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.Environment;

/**
 * A refinement for an environment, used by operations involving quantities. This interface provides some convenience
 * methods, which allow to retrieve certain strategies, without the need of casting.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the fields on which all the operations are based).
 */
public interface QuantityEnvironment<S> extends Environment<S> {

    ErrorPropagationStrategy<S> errorPropagation();

    QuantificationStrategy<S> quantification();

    S confidenceLevel();

}