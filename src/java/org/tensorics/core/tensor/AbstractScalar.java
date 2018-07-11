package org.tensorics.core.tensor;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A base class for scalars, that already guarantees the shape and the correct delegation of the get() methods to the
 * value method.
 *
 * @author kaifox
 * @param <V> the type of the value of the scalar
 */
public abstract class AbstractScalar<V> extends AbstractTensor<V> implements Scalar<V> {

    @Override
    public final V get(Position position) {
        requireNonNull(position, "position must not be null");
        if (Position.empty().equals(position)) {
            return value();
        }
        throw new NoSuchElementException("A scalar contains exactly one value (for the 'empty' position). "
                + "However, it contains no value for the requested position '" + position + "'");
    }

    @Override
    public final V get(Object... coordinates) {
        requireNonNull(coordinates, "coordinates must not be null");
        if (coordinates.length == 0) {
            return value();
        }
        throw new NoSuchElementException("A scalar contains exactly one value (for the 'empty' position). "
                + "However, it contains no value for the requested coordinates '" + Arrays.toString(coordinates) + "'");
    }

    @Override
    public final Shape shape() {
        return Shape.zeroDimensional();
    }

    @Override
    public boolean contains(Position position) {
        return shape().contains(position);
    }
}