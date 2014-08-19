/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
