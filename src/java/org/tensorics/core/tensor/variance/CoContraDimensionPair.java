/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.variance;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a pair of dimensions, related to two different tensors (left, right), where either one has to be the
 * covariant equivalent to the second contravariant dimension.
 * 
 * @author kfuchsbe
 */
public final class CoContraDimensionPair {

    private final Class<?> leftDimension;
    private final Class<?> rightDimension;

    private CoContraDimensionPair(Class<?> leftDimension, Class<?> rightDimension) {
        super();
        checkNotNull(leftDimension, "left dimension must not be null.");
        checkNotNull(rightDimension, "right dimension must not be null.");
        if (!Covariants.isCoContraPair(leftDimension, rightDimension)) {
            throw new IllegalArgumentException("The dimensions [" + leftDimension + "," + rightDimension
                    + "] do not form a co- and contravariant pair.");
        }
        this.leftDimension = leftDimension;
        this.rightDimension = rightDimension;
    }

    public static CoContraDimensionPair ofLeftRight(Class<?> leftDimension, Class<?> rightDimension) {
        return new CoContraDimensionPair(leftDimension, rightDimension);
    }

    public Class<?> left() {
        return leftDimension;
    }

    public Class<?> right() {
        return rightDimension;
    }

    /**
     * Retrieves the covariant part of the pair, no matter if it is left or right
     * 
     * @return the covariant part
     */
    public Class<?> covariant() {
        if (Covariants.isCovariant(leftDimension)) {
            return leftDimension;
        }
        return rightDimension;
    }

    /**
     * Retrieves the contravariant part of the pair, no matter if it is left or right
     * 
     * @return the contravariant part
     */
    public Class<?> contravariant() {
        if (Covariants.isContravariant(leftDimension)) {
            return leftDimension;
        }
        return rightDimension;
    }

    public Object toLeft(Object rightCoordinate) {
        return convertFromTo(rightCoordinate, rightDimension, leftDimension);
    }

    public Object toRight(Object leftCoordinate) {
        return convertFromTo(leftCoordinate, leftDimension, rightDimension);
    }

    private Object convertFromTo(Object coordinate, Class<?> fromDimension, Class<?> toDimension) {
        checkArgument(fromDimension.isInstance(coordinate),
                "The coordinate {} is not compatible with the dimension {}. Cannot convert.", //
                coordinate, fromDimension);
        if (Covariants.isCovariant(fromDimension)) {
            Covariant<?> covariant = (Covariant<?>) coordinate;
            return covariant.get();
        } else {
            @SuppressWarnings("unchecked")
            Class<? extends Covariant<Object>> covariantClass = (Class<? extends Covariant<Object>>) toDimension;
            return Covariants.instantiatorFor(covariantClass).create(coordinate);
        }
    }

}
