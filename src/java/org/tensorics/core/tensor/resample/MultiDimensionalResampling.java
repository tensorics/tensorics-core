package org.tensorics.core.tensor.resample;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;
import org.tensorics.core.tensor.coordinates.PositionOrdering;

public class MultiDimensionalResampling {

    /**
     * Defines the behaviour of the resampling: The order of the dimension in which they appear in the list, defines the
     * order in which the different dimensions are queried.
     */
    private final PositionOrdering positionOrdering;

    private MultiDimensionalResampling(PositionOrdering positionOrdering) {
        this.positionOrdering = requireNonNull(positionOrdering, "positionOrdering must not be null!");
    }

    public static MultiDimensionalResampling of(PositionOrdering positionOrdering) {
        return new MultiDimensionalResampling(positionOrdering);
    }

    public <V> Tensoric<V> resample(Tensor<V> original) {
        Tensoric<V> stage = original;
        for (Class<?> dim : positionOrdering.dimensions()) {
            stage = newStage(stage, dim, original.shape());
        }
        return stage;
    }

    private <V, C> Tensoric<V> newStage(Tensoric<V> prev, Class<C> dimension, Shape originalShape) {
        /* All repeated for the moment. Has to become an input */
        SingleDimensionRepeatingResampler<C, V> repeating = new SingleDimensionRepeatingResampler<>(
                positionOrdering.comparatorFor(dimension));

        return new ResamplingStage<>(originalShape, prev, repeating, dimension);
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
        MultiDimensionalResampling other = (MultiDimensionalResampling) obj;
        if (positionOrdering == null) {
            if (other.positionOrdering != null)
                return false;
        } else if (!positionOrdering.equals(other.positionOrdering))
            return false;
        return true;
    }

}
