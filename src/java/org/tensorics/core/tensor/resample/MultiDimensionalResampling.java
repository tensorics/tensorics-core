package org.tensorics.core.tensor.resample;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;

import org.tensorics.core.tensor.Coordinates;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

/**
 * Combines several one-dimensional resamplers and creates the fully resampled object from them. The final resampling is
 * created by chaining pairs of dimension+resamplers like:
 * 
 * <pre>
 * <code>
 * MultiDimensionalResampling&#60;V&#62; resampling = MultiDimensionalResampling.resample(Coord1.class, coord1Resampler)
 *                                                                      .then(Coord2.class, coord2Resampler)
 *                                                                      [...];
 * </code>
 * </pre>
 * 
 * Note: The order is very relevant here!
 * <p>
 * The actual resampled object can finally be created by calling te {@link #resample(Tensor)} method.
 * 
 * @see #resample(Class, SingleDimensionResampler)
 * @see #then(Class, SingleDimensionResampler)
 * @author kfuchsbe
 * @param <V> the type of the tensor values to resample
 */
public class MultiDimensionalResampling<V> {

    /* This on purpose has to be a linked hashmap, because the insertion order has to be preserved. */
    private final LinkedHashMap<Class<?>, SingleDimensionResampler<?, V>> resamplers;

    private MultiDimensionalResampling(LinkedHashMap<Class<?>, SingleDimensionResampler<?, V>> resamplers) {
        requireNonNull(resamplers, "resamplers must not be null!");

        /* This should never happen, because of the factory methods. Still we keep the check for the moment. */
        if (resamplers.isEmpty()) {
            throw new IllegalArgumentException("No resamplers is defined for ordering.");
        }
        /* A new linked hash set is created in the factory methods. Therefore, no additional copying required here. */
        this.resamplers = resamplers;
    }

    /**
     * Starting point for chaining several dimensino-resampler pairs.
     * 
     * @param dimension the dimension for which the given resampler shall be applied.
     * @param resampler the resampler to apply for the given dimension
     * @return a new (immutable) multidimensional resampling, with exactly one dimension resampled
     * @throws NullPointerException if one of the given arguments is {@code null}
     */
    public static final <C, V> MultiDimensionalResampling<V> resample(Class<C> dimension,
            SingleDimensionResampler<C, V> resampler) {
        requireNonNull(dimension, "dimension must not be null.");
        requireNonNull(resampler, "resampler must not be null.");

        LinkedHashMap<Class<?>, SingleDimensionResampler<?, V>> resamplers = new LinkedHashMap<>();
        resamplers.put(dimension, resampler);
        return new MultiDimensionalResampling<>(resamplers);
    }

    /**
     * Creates a new instance of a multidimensional resamplings, containing the resamplers from {@code this}
     * multidimensional resampling plus the given one.
     * 
     * @param dimension the dimension for which the given resampler shall be used
     * @param dimensionComparator the resampler which shall be used for the given dimension
     * @return a new multidimensional resampling object, containing all the resamplers of {@code this} object plus the
     *         given one.
     */
    public final <C> MultiDimensionalResampling<V> then(Class<C> dimension,
            SingleDimensionResampler<C, V> dimensionComparator) {
        requireNonNull(dimension, "dimension must not be null.");
        requireNonNull(dimensionComparator, "dimensionComparator must not be null.");

        LinkedHashMap<Class<?>, SingleDimensionResampler<?, V>> newResamplers = new LinkedHashMap<>(this.resamplers);
        if (newResamplers.containsKey(dimension)) {
            throw new IllegalArgumentException("You are trying to add an resampler for the dimension '" + dimension
                    + "' twice. This is not allowed.");
        }

        newResamplers.put(dimension, dimensionComparator);
        Coordinates.checkClassesRelations(newResamplers.keySet());
        return new MultiDimensionalResampling<>(newResamplers);
    }

    /**
     * Produces a resampled tensoric object out of the given tensor, by applying the resamplers configured in
     * {@code this} object.
     * 
     * @param original the tensor on which the resampling shall be applied
     * @return a tensoric object, created from the given original tensor with all the resamplers applied
     */
    public Tensoric<V> resample(Tensor<V> original) {
        Tensoric<V> stage = original;
        for (Class<?> dimension : resamplers.keySet()) {
            stage = newStage(stage, dimension, original.shape());
        }
        return stage;
    }

    private <C> Tensoric<V> newStage(Tensoric<V> prev, Class<C> dimension, Shape originalShape) {
        return new ResamplingStage<>(originalShape, prev, resamplerFor(dimension), dimension);
    }

    @SuppressWarnings("unchecked")
    private <C> SingleDimensionResampler<C, V> resamplerFor(Class<C> dimension) {
        return (SingleDimensionResampler<C, V>) resamplers.get(dimension);
    }

}
