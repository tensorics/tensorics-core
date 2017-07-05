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

package org.tensorics.core.tensor;

import org.tensorics.core.commons.util.AbstractPair;

/**
 * A pair of positions, usually representing a position in the left tensor of an operation and its corresponding
 * position in the right tensor.
 * 
 * @author kfuchsbe
 */
public final class PositionPair extends AbstractPair<Position> {

    /**
     * Private constructor, to enforce usage of the factory method.
     * 
     * @param leftPosition the position within a left tensor of an operation
     * @param rightPosition the position within the right tensor of an operation
     */
    private PositionPair(Position leftPosition, Position rightPosition) {
        super(leftPosition, rightPosition);
    }

    public static PositionPair fromLeftRight(Position leftPosition, Position rightPosition) {
        return new PositionPair(leftPosition, rightPosition);
    }

}
