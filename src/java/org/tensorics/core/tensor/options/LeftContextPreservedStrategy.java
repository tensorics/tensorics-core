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
package org.tensorics.core.tensor.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.tensor.Position;

public class LeftContextPreservedStrategy implements ContextPropagationStrategy {

    /**
     * @deprecated use factory method
     */
    @Deprecated
    public LeftContextPreservedStrategy() {
        /* nothing, to be changed to private */
    }

    public static LeftContextPreservedStrategy get() {
        return new LeftContextPreservedStrategy();
    }

    @Override
    public Position contextForLeftRight(Position leftContext, Position rightContext) {
        return leftContext;
    }

    @Override
    public Class<? extends ManipulationOption> getMarkerInterface() {
        return ContextPropagationStrategy.class;
    }
}
