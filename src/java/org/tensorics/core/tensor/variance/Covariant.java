/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.variance;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Coordinates inheriting from this class mark are treated as covariant axis. The type of 'variance' is defined, as in
 * tensor-algebra, by transformation of the values of the tensor when transforming the basis of the values.
 * <p>
 * 'Physical' vectors/quantities usually transform in such a way, that they transform inverse to the basis. For example,
 * lets assume a vector of 3 values, representing karthesian coordinates in a certain unit. If now the basis vectors
 * would be multiplied by a factor, the values of the vector would have to be divided by the same factor. This
 * transformation property is called 'contravariance'.
 * <p>
 * If the vectors would transform in the same way as the basis, we would say the vector (tensor in that direction)
 * transforms 'covariant'. This is for example the case for tensors that represent transformations themselves.
 * <p>
 * Within the tensorics framework, because of the forementioned reasons, the contravariant case is considered as the
 * default. Simply any object can be put as contravariant coordinate. On the other hand, covariant axes have to be
 * marked explicitely, by inheriting from this class.
 * <p>
 * All sub classes are obliged to provide a public constructor with a single argument, taking the coordinate itself as
 * an argument. This inheritance structure is necessary, to be able to have several covariant coordinates in one tensor
 * (because tensorics treats coordinates of the same class as the same coordinate-dimension).
 * 
 * @param <C> the type of the (contravariant!) coordinate
 * @see <a href="http://en.wikipedia.org/wiki/Covariance_and_contravariance_of_vectors">
 *      http://en.wikipedia.org/wiki/Covariance_and_contravariance_of_vectors</a>
 * @author kfuchsbe
 */
public class Covariant<C> {

    private final C coordinate;

    /**
     * The single-argument constructor that has to be overriden by subclasses. It takes the coordinate itself.
     * 
     * @param coordinate the coordinate, which will be treated as a covariant axis
     * @throws NullPointerException if the given coordinate is null
     */
    protected Covariant(C coordinate) {
        this.coordinate = checkNotNull(coordinate, "coordinate must not be null!");
    }

    /**
     * Retrieves the coordinate value from the covariant coordinate.
     * 
     * @return the coordinate value
     */
    public C get() {
        return this.coordinate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Covariant<?> other = (Covariant<?>) obj;
        if (coordinate == null) {
            if (other.coordinate != null) {
                return false;
            }
        } else if (!coordinate.equals(other.coordinate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + coordinate + "]";
    }
}
