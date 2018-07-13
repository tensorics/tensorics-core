package org.tensorics.core.tensor.resample;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;

import org.tensorics.core.tensor.Coordinates;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

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

    public static final <C, V> MultiDimensionalResampling<V> resample(Class<C> dimension,
            SingleDimensionResampler<C, V> resampler) {
        requireNonNull(dimension, "dimension must not be null.");
        requireNonNull(resampler, "resampler must not be null.");

        LinkedHashMap<Class<?>, SingleDimensionResampler<?, V>> resamplers = new LinkedHashMap<>();
        resamplers.put(dimension, resampler);
        return new MultiDimensionalResampling<>(resamplers);
    }

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
