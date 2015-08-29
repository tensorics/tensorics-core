/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static org.tensorics.core.tensor.operations.PositionFunctions.forSupplier;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.lang.OngoingCompletion;
import org.tensorics.core.tensor.lang.OngoingFlattening;
import org.tensorics.core.tensor.lang.OngoingTensorManipulation;
import org.tensorics.core.tensor.lang.QuantityTensors;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tensor.operations.FunctionTensorCreationOperation;
import org.tensorics.core.tensor.operations.PositionFunctions;
import org.tensorics.core.tensor.operations.SingleValueTensorCreationOperation;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedBuilder;
import org.tensorics.core.tensorbacked.Tensorbackeds;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Supplier;

/**
 * The main entry point for constructing and structural manipulation of tensorics. If mathematical operations are
 * required, then you should either inherit from {@link TensoricSupport} (or one of its convenience implementations) or
 * use the {@link #using(ExtendedField)} method (only recommended for one-liners).
 * <p>
 * Additional utilities for supporting classes can be found in the corresponding utility classes. E.g.
 * <ul>
 * <li> {@link org.tensorics.core.tensor.Positions}
 * <li> {@link org.tensorics.core.tensor.Shapes}
 * <li> {@link QuantityTensors}
 * <li> {@link Tensorbackeds}
 * </ul>
 * 
 * @author kfuchsbe, agorzaws
 */
@SuppressWarnings("PMD.TooManyMethods")
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
     * @return a tensoric support, which provides the starting methods for the tensoric fluent API.
     * @param <E> the types of values in the field
     */
    public static <E> TensoricSupport<E> using(ExtendedField<E> field) {
        return new TensoricSupport<>(EnvironmentImpl.of(field, ManipulationOptions.defaultOptions(field)));
    }

    /**
     * @see TensorStructurals#merge(Set)
     */
    public static <E> Tensor<E> merge(Set<Tensor<E>> tensors) {
        return TensorStructurals.merge(tensors);
    }

    /**
     * @see TensorStructurals#mergeTo(Set, Class)
     */
    public static <TB extends Tensorbacked<E>, TBOUT extends Tensorbacked<E>, E> TBOUT mergeTo(Set<TB> toBeMerged,
            Class<TBOUT> classToReturn) {
        return TensorStructurals.mergeTo(toBeMerged, classToReturn);
    }

    /**
     * @see ImmutableTensor#builder(Set)
     */
    public static <T> TensorBuilder<T> builder(Set<? extends Class<?>> dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    /**
     * @see ImmutableTensor#builder(Class...)
     */
    public static <T> TensorBuilder<T> builder(Class<?>... dimensions) {
        return ImmutableTensor.builder(dimensions);
    }

    /**
     * @see ImmutableTensor#fromMap(Set, Map)
     */
    public static <T> Tensor<T> fromMap(Set<? extends Class<?>> dimensions, Map<Position, T> map) {
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
     * @see ImmutableTensor#zeroDimensionalOf(Object)
     */
    public static <T> Tensor<T> zeroDimensionalOf(T value) {
        return ImmutableTensor.zeroDimensionalOf(value);
    }

    /**
     * @see ImmutableQuantifiedValue#of(Object, Unit)
     */
    public static <V> ImmutableQuantifiedValue<V> quantityOf(V value, Unit unit) {
        return ImmutableQuantifiedValue.of(value, unit);
    }

    /**
     * Convenience method to create a quantity directly from a jscience unit.
     * 
     * @param value the of the quantity
     * @param unit the unit of the quantity
     * @return a new instance of the quantity
     * @see Tensorics#quantityOf(Object, Unit)
     */
    public static <V> ImmutableQuantifiedValue<V> quantityOf(V value, javax.measure.unit.Unit<?> unit) {
        return quantityOf(value, JScienceUnit.of(unit));
    }

    /**
     * @see Tensorbackeds#builderFor(Class)
     */
    public static <V, TB extends Tensorbacked<V>> TensorbackedBuilder<V, TB> builderFor(Class<TB> tensorbackedClass) {
        return Tensorbackeds.builderFor(tensorbackedClass);
    }

    /**
     * @see Tensorbackeds#sizeOf(Tensorbacked)
     */
    public static <TB extends Tensorbacked<?>> int sizeOf(TB tensorbacked) {
        return Tensorbackeds.sizeOf(tensorbacked);
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

    /**
     * @see TensorStructurals#from(Tensor)
     */
    public static <V> OngoingTensorManipulation<V> from(Tensor<V> tensor) {
        return TensorStructurals.from(tensor);
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
        return new SingleValueTensorCreationOperation<S>(shape, value).perform();
    }

    public static <S> Tensor<S> createFrom(Shape shape, Supplier<S> supplier) {
        return new FunctionTensorCreationOperation<>(shape, forSupplier(supplier)).perform();
    }

    public static <S> Tensor<S> createFrom(Shape shape, Function<Position, S> function) {
        return new FunctionTensorCreationOperation<>(shape, function).perform();
    }

    public static <S> OngoingCompletion<S> complete(Tensor<S> tensor) {
        return TensorStructurals.complete(tensor);
    }

    public static <S, T> Tensor<T> transformEntries(Tensor<S> tensor, Function<Entry<Position, S>, T> function) {
        return TensorStructurals.transformEntries(tensor, function);
    }

    public static <S, T> Tensor<T> map(Tensor<S> tensor, Function<S, T> function) {
        return TensorStructurals.transformScalars(tensor, function);
    }

    public static final Tensor<QuantifiedValue<Double>> zeroDimensionalOf(double value, javax.measure.unit.Unit<?> unit) {
        QuantifiedValue<Double> quantity = quantityOf(value, unit);
        return zeroDimensionalOf(quantity);
    }

    public static final Tensor<QuantifiedValue<Double>> zeroDimensionalOf(double value, Unit unit) {
        QuantifiedValue<Double> quantity = quantityOf(value, unit);
        return zeroDimensionalOf(quantity);
    }

}
