// @formatter:off
/*******************************************************************************
 *
 * This file is part of tensorics.
 *
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.lang;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableScalar;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Scalar;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensorbacked.dimtyped.*;
import org.tensorics.core.tensor.lang.OngoingCompletion;
import org.tensorics.core.tensor.lang.OngoingFlattening;
import org.tensorics.core.tensor.lang.OngoingResamplingStart;
import org.tensorics.core.tensor.lang.OngoingTensorFiltering;
import org.tensorics.core.tensor.lang.OngoingTensorManipulation;
import org.tensorics.core.tensor.lang.QuantityTensors;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensor.operations.TensorInternals;
import org.tensorics.core.tensor.stream.TensorStreams;
import org.tensorics.core.tensorbacked.OngoingTensorbackedCompletion;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.SimpleTensorbackedBuilder;
import org.tensorics.core.tensorbacked.Tensorbackeds;
import org.tensorics.core.tensorbacked.lang.OngoingTensorbackedConstruction;
import org.tensorics.core.tensorbacked.lang.OngoingTensorbackedFiltering;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

import com.google.common.base.Optional;

/**
 * The main entry point for constructing and structural manipulation of tensorics. If mathematical operations are
 * required, then you should either inherit from {@link TensoricSupport} (or one of its convenience implementations) or
 * use the {@link #using(ExtendedField)} method (only recommended for one-liners).
 * <p>
 * Additional utilities for supporting classes can be found in the corresponding utility classes. E.g.
 * <ul>
 * <li>{@link org.tensorics.core.tensor.Positions}
 * <li>{@link org.tensorics.core.tensor.Shapes}
 * <li>{@link QuantityTensors}
 * <li>{@link Tensorbackeds}
 * </ul>
 *
 * @author kfuchsbe, agorzaws, mihostet
 */
public final class Tensorics {

    /**
     * private constructor to avoid instantiation
     */
    private Tensorics() {
        /* only static methods */
    }

    /**
     * Creates an instance of a 'support' type class, which provides methods as starting points for the 'fluent' API for
     * tensor manipulation.
     *
     * @param field the (mathematical construct) field which defines the operations for the tensor elements.
     * @param <E>   the types of values in the field
     * @return a tensoric support, which provides the starting methods for the tensoric fluent API.
     */
    public static <E> TensoricSupport<E> using(ExtendedField<E> field) {
        return new TensoricSupport<>(EnvironmentImpl.of(field, ManipulationOptions.defaultOptions(field)));
    }

    public static <E> Tensor<E> merge(Iterable<Tensor<E>> tensors) {
        return TensorStructurals.merge(tensors);
    }

    /**
     * @see TensorStructurals#mergeContextIntoShape(Tensor)
     */
    public static <E> Tensor<E> mergeContextIntoShape(Tensor<E> tensor) {
        return TensorStructurals.mergeContextIntoShape(tensor);
    }

    /**
     * @see ImmutableTensor#builder(Iterable)
     */
    public static <T> TensorBuilder<T> builder(Iterable<Class<?>> dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    /**
     * @see ImmutableTensor#builder(Class...)
     */
    public static <T> TensorBuilder<T> builder(Class<?>... dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    public static <T> TensorbackedScalarBuilder<T, TensorbackedScalar<T>> builderBacked() {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(), TensorbackedScalarBuilder.class, TensorbackedScalar.class);
    }

    public static <C1, T> Tensorbacked1dBuilder<C1, T, Tensorbacked1d<C1, T>> builderBacked(Class<C1> dimension1) {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(dimension1), Tensorbacked1dBuilder.class, Tensorbacked1d.class);
    }

    public static <C1, C2, T> Tensorbacked2dBuilder<C1, C2, T, Tensorbacked2d<C1, C2, T>> builderBacked(Class<C1> d1, Class<C2> d2) {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(d1, d2), Tensorbacked2dBuilder.class, Tensorbacked2d.class);
    }

    public static <C1, C2, C3, T> Tensorbacked3dBuilder<C1, C2, C3, T, Tensorbacked3d<C1, C2, C3, T>> builderBacked(Class<C1> d1, Class<C2> d2, Class<C3> d3) {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(d1, d2, d3), Tensorbacked3dBuilder.class, Tensorbacked3d.class);
    }

    public static <C1, C2, C3, C4, T> Tensorbacked4dBuilder<C1, C2, C3, C4, T, Tensorbacked4d<C1, C2, C3, C4, T>> builderBacked(Class<C1> d1, Class<C2> d2, Class<C3> d3, Class<C4> d4) {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(d1, d2, d3, d4), Tensorbacked4dBuilder.class, Tensorbacked4d.class);
    }

    public static <C1, C2, C3, C4, C5, T> Tensorbacked5dBuilder<C1, C2, C3, C4, C5, T, Tensorbacked5d<C1, C2, C3, C4, C5, T>> builderBacked(Class<C1> d1, Class<C2> d2, Class<C3> d3, Class<C4> d4, Class<C5> d5) {
        return DimtypedTensorbackedBuilderImpl.from(ImmutableTensor.builder(d1, d2, d3, d4, d5), Tensorbacked5dBuilder.class, Tensorbacked5d.class);
    }

    /**
     * @see ImmutableTensor#fromMap(Iterable, Map)
     */
    public static <T> Tensor<T> fromMap(Iterable<Class<?>> dimensions, Map<Position, T> map) {
        return ImmutableTensor.fromMap(dimensions, map);
    }

    /**
     * @see ImmutableTensor#copyOf(Tensor)
     */
    public static <T> Tensor<T> copyOf(Tensor<T> tensor) {
        return ImmutableTensor.copyOf(tensor);
    }

    /**
     * @see ImmutableTensor#builderFrom(Tensor)
     */
    public static <T> TensorBuilder<T> builderFrom(Tensor<T> tensor) {
        return ImmutableTensor.builderFrom(tensor);
    }

    /**
     * @see ImmutableScalar#of(Object)
     */
    public static <T> ImmutableScalar<T> scalarOf(T value) {
        return ImmutableScalar.of(value);
    }

    /**
     * @see ImmutableQuantifiedValue#of(Object, Unit)
     */
    public static <V> ImmutableQuantifiedValue<V> quantityOf(V value, Unit unit) {
        return ImmutableQuantifiedValue.of(value, unit);
    }

    /**
     * Convenience method to create a quantity directly from a jscience base.
     *
     * @param value the of the quantity
     * @param unit  the base of the quantity
     * @return a new instance of the quantity
     * @see Tensorics#quantityOf(Object, Unit)
     */
    public static <V> ImmutableQuantifiedValue<V> quantityOf(V value, javax.measure.unit.Unit<?> unit) {
        return quantityOf(value, JScienceUnit.of(unit));
    }

    /**
     * @see Tensorbackeds#builderFor(Class)
     */
    public static <V, TB extends Tensorbacked<V>> SimpleTensorbackedBuilder<V, TB> builderFor(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderForScalar(Class) 
     */
    public static <V, TB extends TensorbackedScalar< V>> TensorbackedScalarBuilder<V, TB> builderForScalar(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderForScalar(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderFor1D(Class)
     */
    public static <C1, V, TB extends Tensorbacked1d<C1, V>> Tensorbacked1dBuilder<C1, V, TB> builderFor1D(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor1D(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderFor2D(Class)
     */
    public static <C1, C2, V, TB extends Tensorbacked2d<C1, C2, V>> Tensorbacked2dBuilder<C1, C2, V, TB> builderFor2D(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor2D(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderFor3D(Class)
     */
    public static <C1, C2, C3, V, TB extends Tensorbacked3d<C1, C2, C3, V>> Tensorbacked3dBuilder<C1, C2, C3, V, TB> builderFor3D(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor3D(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderFor4D(Class)
     */
    public static <C1, C2, C3, C4, V, TB extends Tensorbacked4d<C1, C2, C3, C4, V>> Tensorbacked4dBuilder<C1, C2, C3, C4, V, TB> builderFor4D(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor4D(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#builderFor5D(Class)
     */
    public static <C1, C2, C3, C4, C5, V, TB extends Tensorbacked5d<C1, C2, C3, C4, C5, V>> Tensorbacked5dBuilder<C1, C2, C3, C4, C5, V, TB> builderFor5D(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor5D(tensorbackedClass);
    }


    /**
     * @see Tensorbackeds#sizeOf(Tensorbacked)
     */
    public static <TB extends Tensorbacked<?>> int sizeOf(TB tensorbacked) {
        return Tensorbackeds.sizeOf(tensorbacked);
    }

    public static int sizeOf(Tensor<?> tensor) {
        return tensor.shape().size();
    }

    /**
     * @see Tensorbackeds#dimensionalityOf(Tensorbacked)
     */
    public static <TB extends Tensorbacked<?>> int dimensionalityOf(TB tensorbacked) {
        return Tensorbackeds.dimensionalityOf(tensorbacked);
    }

    /**
     * @see Tensorbackeds#empty(Class)
     */
    public static <V, TB extends Tensorbacked<V>> TB empty(Class<TB> tensorbackedClass) {
        return Tensorbackeds.empty(tensorbackedClass);
    }

    public static <V> Tensor<V> empty(Iterable<Class<?>> dimensions) {
        return ImmutableTensor.<V>builder(dimensions).build();
    }

    public static <V> Tensor<V> empty(Class<?>... dimensions) {
        return ImmutableTensor.<V>builder(dimensions).build();
    }

    /**
     * @see Tensorbackeds#validitiesOf(Tensorbacked)
     */
    public static <S> Tensor<Boolean> validitiesOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return Tensorbackeds.validitiesOf(tensorbacked);
    }

    /**
     * @see Tensorbackeds#valuesOf(Tensorbacked)
     */
    public static <S> Tensor<S> valuesOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return Tensorbackeds.valuesOf(tensorbacked);
    }

    /**
     * @see Tensorbackeds#errorsOf(Tensorbacked)
     */
    public static <S> Tensor<Optional<S>> errorsOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return Tensorbackeds.errorsOf(tensorbacked);
    }

    /**
     * @see Tensorbackeds#positionsOf(Tensorbacked)
     */
    public static Set<Position> positionsOf(Tensorbacked<?> tensorbacked) {
        return Tensorbackeds.positionsOf(tensorbacked);
    }

    /**
     * @deprecated use renamed method {@link #quantityTensorOf(Tensor, Unit)}
     */
    @Deprecated
    public static <S> Tensor<QuantifiedValue<S>> convertToQuantified(Tensor<S> tensor, Unit unit) {
        return QuantityTensors.quantityTensorOf(tensor, unit);
    }

    /**
     * @see QuantityTensors#quantityTensorOf(Tensor, Unit)
     */
    public static <S> Tensor<QuantifiedValue<S>> quantityTensorOf(Tensor<S> tensor, Unit unit) {
        return QuantityTensors.quantityTensorOf(tensor, unit);
    }

    /**
     * @see QuantityTensors#quantityTensorOf(Tensor, javax.measure.unit.Unit)
     */
    public static <S> Tensor<QuantifiedValue<S>> quantityTensorOf(Tensor<S> tensor, javax.measure.unit.Unit<?> unit) {
        return QuantityTensors.quantityTensorOf(tensor, JScienceUnit.of(unit));
    }

    /**
     * @see QuantityTensors#unitOf(Tensor)
     */
    public static <S> Unit unitOf(Tensor<QuantifiedValue<S>> tensor) {
        return QuantityTensors.unitOf(tensor);
    }

    /**
     * @see Tensorbackeds#unitOf(Tensorbacked)
     */
    public static <S> Unit unitOf(Tensorbacked<QuantifiedValue<S>> tensorbacked) {
        return Tensorbackeds.unitOf(tensorbacked);
    }

    /**
     * @see Tensorbackeds#shapeOf(Tensorbacked)
     */
    public static Shape shapeOf(Tensorbacked<?> tensorbacked) {
        return Tensorbackeds.shapeOf(tensorbacked);
    }

    public static Set<Class<?>> dimensionsOf(Tensorbacked<?> tensorbacked) {
        return Tensorbackeds.shapeOf(tensorbacked).dimensionSet();
    }

    /**
     * @see TensorStructurals#from(Tensor)
     */
    public static <V> OngoingTensorManipulation<V> from(Tensor<V> tensor) {
        return TensorStructurals.from(tensor);
    }

    public static <V> OngoingTensorManipulation<V> from(Tensorbacked<V> tensorbacked) {
        return TensorStructurals.from(tensorbacked.tensor());
    }

    public static Set<Class<?>> dimensionsOf(Tensor<?> tensor) {
        return tensor.shape().dimensionSet();
    }

    public static Set<Position> positionsOf(Tensor<?> tensor) {
        return tensor.shape().positionSet();
    }

    public static <S> Tensor<Boolean> validitiesOf(Tensor<QuantifiedValue<S>> tensor) {
        return QuantityTensors.validitiesOf(tensor);
    }

    public static <S> Tensor<S> valuesOf(Tensor<QuantifiedValue<S>> tensor) {
        return QuantityTensors.valuesOf(tensor);
    }

    public static <S> Tensor<Optional<S>> errorsOf(Tensor<QuantifiedValue<S>> tensor) {
        return QuantityTensors.errorsOf(tensor);
    }

    public static <S> Tensor<S> errorsOfOr(Tensor<QuantifiedValue<S>> tensor, S defaultValue) {
        return QuantityTensors.errorsOfOr(tensor, defaultValue);
    }

    public static <S> OngoingFlattening<S> flatten(Tensor<S> tensor) {
        return TensorStructurals.flatten(tensor);
    }

    public static <S> OngoingFlattening<S> flatten(Tensorbacked<S> tensorbacked) {
        return Tensorbackeds.flatten(tensorbacked);
    }

    public static <S> Tensor<S> sameValues(Shape shape, S value) {
        return TensorInternals.sameValues(shape, value);
    }

    /**
     * @deprecated use {@link #tensor(Shape, Supplier)}
     */
    @Deprecated
    public static <S> Tensor<S> createFrom(Shape shape, Supplier<S> supplier) {
        return tensor(shape, supplier);
    }

    public static <S> Tensor<S> tensor(Shape shape, Supplier<S> supplier) {
        return TensorInternals.createFrom(shape, supplier);
    }

    /**
     * @deprecated use {@link #tensor(Shape, Function)}
     */
    @Deprecated
    public static <S> Tensor<S> createFrom(Shape shape, Function<Position, S> function) {
        return tensor(shape, function);
    }

    public static <S> Tensor<S> tensor(Shape shape, Function<Position, S> function) {
        return TensorInternals.createFrom(shape, function);
    }

    public static <S> OngoingCompletion<S> complete(Tensor<S> tensor) {
        return TensorStructurals.complete(tensor);
    }

    /**
     * @deprecated use {@link #map(Tensor, BiFunction)} instead
     */
    @Deprecated
    public static <S, T> Tensor<T> transformEntries(Tensor<S> tensor, Function<Entry<Position, S>, T> function) {
        return TensorStructurals.transformEntries(tensor, function);
    }

    public static <S, T> Tensor<T> map(Tensor<S> tensor, Function<S, T> function) {
        return TensorStructurals.transformScalars(tensor, function);
    }

    public static <S, T> Tensor<T> map(Tensor<S> tensor, BiFunction<Position, S, T> function) {
        return TensorStructurals.transformScalars(tensor, function);
    }

    public static <S> void forEach(Tensorbacked<S> tensorBacked, Consumer<S> consumer) {
        forEach(tensorBacked.tensor(), consumer);
    }

    public static <S> void forEach(Tensorbacked<S> tensorBacked, BiConsumer<Position, S> consumer) {
        forEach(tensorBacked.tensor(), consumer);
    }

    public static <S> void forEach(Tensor<S> tensor, Consumer<S> consumer) {
        TensorStructurals.consumeScalars(tensor, consumer);
    }

    public static <S> void forEach(Tensor<S> tensor, BiConsumer<Position, S> consumer) {
        TensorStructurals.consumeScalars(tensor, consumer);
    }

    public static Scalar<QuantifiedValue<Double>> zeroDimensionalOf(double value,
                                                                    javax.measure.unit.Unit<?> unit) {
        QuantifiedValue<Double> quantity = quantityOf(value, unit);
        return scalarOf(quantity);
    }

    public static Scalar<QuantifiedValue<Double>> zeroDimensionalOf(double value, Unit unit) {
        QuantifiedValue<Double> quantity = quantityOf(value, unit);
        return scalarOf(quantity);
    }

    /**
     * @see Tensorbackeds#tensorsOf(Iterable)
     */
    public static <S> Iterable<Tensor<S>> tensorsOf(Iterable<? extends Tensorbacked<S>> tensorbackeds) {
        return Tensorbackeds.tensorsOf(tensorbackeds);
    }

    /**
     * @see Tensorbackeds#construct(Class)
     */
    public static <V, TB extends Tensorbacked<V>> OngoingTensorbackedConstruction<V, TB> construct(
            Class<TB> tensorbackedClass) {
        return Tensorbackeds.construct(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#stripContext(Tensorbacked)
     */
    public static <V, TB extends Tensorbacked<V>> TB stripContext(TB tensorbacked) {
        return Tensorbackeds.stripContext(tensorbacked);
    }

    /**
     * @see TensorStructurals#stripContext(Tensor)
     */
    public static <S> Tensor<S> stripContext(Tensor<S> tensor) {
        return TensorStructurals.stripContext(tensor);
    }

    /**
     * @see TensorStructurals#filter(Tensor)
     */
    public static <S> OngoingTensorFiltering<S> filter(Tensor<S> tensor) {
        return TensorStructurals.filter(tensor);
    }

    /**
     * @see Tensorbackeds#filter(Tensorbacked)
     */
    public static <V, TB extends Tensorbacked<V>> OngoingTensorbackedFiltering<V, TB> filter(TB tensorbacked) {
        return Tensorbackeds.filter(tensorbacked);
    }

    public static <S> Tensor<S> setContext(Tensor<S> tensor, Position context) {
        return TensorStructurals.setContext(tensor, context);
    }

    public static <V, TB extends Tensorbacked<V>> TB setContext(TB tensorbacked, Position context) {
        return Tensorbackeds.setContext(tensorbacked, context);
    }

    /**
     * @see TensorStreams#tensorEntryStream(Tensor)
     */
    public static <S> Stream<Map.Entry<Position, S>> stream(Tensor<S> tensor) {
        return TensorStreams.tensorEntryStream(tensor);
    }

    /**
     * @see TensorStreams#tensorEntryStream(Tensor)
     */
    public static <S> Stream<Map.Entry<Position, S>> stream(Tensorbacked<S> tensorBacked) {
        return TensorStreams.tensorEntryStream(tensorBacked.tensor());
    }

    /**
     * @see Tensorbackeds#shapeOf(Tensorbacked)
     */
    public static <TB extends Tensorbacked<?>> Iterable<Shape> shapesOf(Iterable<TB> tensorbackeds) {
        return Tensorbackeds.shapesOf(tensorbackeds);
    }

    /**
     * @see Tensorbackeds#complete(Tensorbacked)
     */
    public static <S, TB extends Tensorbacked<S>> OngoingTensorbackedCompletion<TB, S> complete(TB tensorbacked) {
        return Tensorbackeds.complete(tensorbacked);
    }

    /**
     * @see TensorInternals#mapFrom(Tensor)
     */
    public static <V> Map<Position, V> mapFrom(Tensor<V> tensor) {
        return TensorInternals.mapFrom(tensor);
    }

    /**
     * @see Position#of(Iterable)
     */
    public static Position at(Iterable<?> coordinates) {
        return Position.of(coordinates);
    }

    /**
     * @see Position#of(Object...)
     */
    public static Position at(Object... coordinates) {
        return Position.of(coordinates);
    }

    /**
     * @see TensorStructurals#resample(Tensor)
     */
    public static <V> OngoingResamplingStart<V> resample(Tensor<V> tensor) {
        return TensorStructurals.resample(tensor);
    }

}
