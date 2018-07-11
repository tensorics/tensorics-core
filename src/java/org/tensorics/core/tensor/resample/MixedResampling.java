package org.tensorics.core.tensor.resample;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.coordinates.PositionOrdering;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.google.common.collect.Lists.reverse;
import static java.util.Objects.requireNonNull;
import static org.tensorics.core.tensor.Positions.difference;
import static org.tensorics.core.tensor.Positions.stripping;
import static org.tensorics.core.tensor.lang.TensorStructurals.from;

public class MixedResampling implements ResamplingStrategy {

    /**
     * Defines the behaviour of the resampling: The order of the dimension in which they appear in the list, defines the
     * order in which the different dimensions are queried.
     */
    private final PositionOrdering positionOrdering;

    private MixedResampling(PositionOrdering positionOrdering) {
        this.positionOrdering = requireNonNull(positionOrdering, "positionOrdering must not be null!");
    }

    public static MixedResampling of(PositionOrdering positionOrdering) {
        return new MixedResampling(positionOrdering);
    }

    @Override
    public <V> V resample(Tensor<V> originalTensor, Position position) {
        Shape shape = originalTensor.shape();
        if (shape.contains(position)) {
            return originalTensor.get(position);
        }

        Position forcedMatchingPosition = stripping(ImmutableSet.copyOf(positionOrdering.dimensions())).apply(position);
        Position resamplingPosition = difference(position, forcedMatchingPosition);

        Tensor<V> resamplingTensor = from(originalTensor).extract(forcedMatchingPosition);

        Function<Position, V> valueFunction = (p) -> {
            return originalTensor.get(p);
        };
        Position pos = resamplingPosition;
        

        for (Class<?> dim : positionOrdering.dimensions()) {

            /* All repeated for the moment. Has to become an input */
            SingleDimensionRepeatingResampler<?, V> repeating = new SingleDimensionRepeatingResampler<>(
                    positionOrdering.comparatorFor(dim));

            Object coordinate = resamplingPosition.coordinateFor(dim);

            Set<?> coordinates = resamplingTensor.shape().coordinatesOfType(dim);

            // repeating.resample(coordinates, valuesCallback, coordinate)

            /* TODO continue here */

        }

        
        
        // xxx
        
        
        // xxx
        
        
        
        
        throw new NoSuchElementException("Tensor cannot be resampled at '" + position + "'");
    }
   
    @Override
    public String toString() {
        return "RepeatingResamplingStrategy [positionOrdering=" + positionOrdering + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((positionOrdering == null) ? 0 : positionOrdering.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MixedResampling other = (MixedResampling) obj;
        if (positionOrdering == null) {
            if (other.positionOrdering != null)
                return false;
        } else if (!positionOrdering.equals(other.positionOrdering))
            return false;
        return true;
    }

}
