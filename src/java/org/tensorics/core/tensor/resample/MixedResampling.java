package org.tensorics.core.tensor.resample;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Positions;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.coordinates.PositionOrdering;

import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.function.Function;

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


        Function<?, V> valueFunction = (o) -> {
            Position combined =Positions.union(forcedMatchingPosition, Position.of(o));
            return null;
        };

        for (Class<?> dim : positionOrdering.dimensions()) {
            /* Not yet complete (comparator) */
            SingleDimensionRepeatingResamplingStrategy<?, V> repeating = new SingleDimensionRepeatingResamplingStrategy<>();

        }




        SortedSet<Position> orderedPositions = ImmutableSortedSet.copyOf(positionOrdering.positionComparator(),
                resamplingTensor.shape().positionSet());

        SortedSet<Position> before = orderedPositions.headSet(resamplingPosition);
        if (!before.isEmpty()) {
            return resamplingTensor.get(before.last());
        }

        SortedSet<Position> after = orderedPositions.tailSet(resamplingPosition);
        if (!after.isEmpty()) {
            return resamplingTensor.get(after.first());
        }

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
