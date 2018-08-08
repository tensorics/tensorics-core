package org.tensorics.core.tensor.resample;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

/**
 * Defines a strategy how to resample in one single dimension of a tensor.
 *
 * @author kfuchsbe
 * @param <C> the type of the coordinates in the direction to resample
 * @param <V> the type of the values of the tensors
 */
public interface SingleDimensionResampler<C, V> {

    /**
     * Has to return {@code true} if the resampling will be possible, provided the the given parameters.
     * 
     * @param supportCoordinates the supporting points, which can be used as input to resample
     * @param resampleCoordinate the coordinate at which the resampling shall be performed
     * @return {@code true} if the resampling will be possible with the provided input, {@code false} if not.
     */
    boolean canResample(Set<C> supportCoordinates, C resampleCoordinate);

    /**
     * Performs the actual resampling using the provided input.
     * 
     * @param supportCoordinates the supporting points, which can be used as input to resample
     * @param valuesCallback a function providing values of type V for all given coordinates
     * @param resampleCoordinate the coordinate at which the resampling shall be performed
     * @return {@code true} if the resampling will be possible with the provided input, {@code false} if not.
     * @throws NoSuchElementException if the resampling cannot be performed for any reason
     */
    V resample(Set<C> supportCoordinates, Function<C, V> valuesCallback, C resampleCoordinate);

}
