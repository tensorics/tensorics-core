/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

/**
 * The detection class for changes in Tensors. <br>
 * So far it only supports one dimension tensors.
 * 
 * @author agorzaws
 * @param <C> type values in the tensor.
 */
public class TensorChangeDetectionOperation<C> implements Conversion<Tensor<Boolean>, Iterable<C>> {

    private final Class<C> direction;
    private final Comparator<? super C> comparator;

    private TensorChangeDetectionOperation(Class<C> direction, Comparator<? super C> comparator) {
        this.direction = Objects.requireNonNull(direction, "direction must not be null");
        this.comparator = Objects.requireNonNull(comparator, "comparator must not be null");

    }

    public static <C> TensorChangeDetectionOperation<C> of(Class<C> direction, Comparator<? super C> comparator) {
        return new TensorChangeDetectionOperation<C>(direction, comparator);
    }

    public static <C extends Comparable<C>> TensorChangeDetectionOperation<C> of(Class<C> direction) {
        return new TensorChangeDetectionOperation<C>(direction, Comparator.naturalOrder());
    }

    @Override
    public Iterable<C> apply(Tensor<Boolean> tensor) {
        Shape shape = tensor.shape();

        if (shape.dimensionality() > 1) {
            throw new IllegalArgumentException(
                    "Detecting changes is currently only supported for 1-dimensional tensors. However, the provided tensor has "
                            + shape.dimensionality() + " dimensions.");
        }
        if (!shape.dimensionSet().contains(direction)) {
            throw new IllegalArgumentException("Provided tensor " + tensor.shape().dimensionSet()
                    + " doesn't have requested detection direction class '" + direction + "'");
        }

        List<C> coordinates = new ArrayList<>(shape.coordinatesOfType(direction));
        Collections.sort(coordinates, comparator);

        List<C> changes = new ArrayList<>();
        Boolean lastValue = null;
        for (C coordinate : coordinates) {
            Boolean newValue = tensor.get(coordinate);
            if ((lastValue != null) && (lastValue ^ newValue)) {
                changes.add(coordinate);
            }
            lastValue = newValue;
        }
        return changes;
    }

}
