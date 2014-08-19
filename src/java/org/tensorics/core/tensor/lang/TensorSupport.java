/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.iterable.lang.ScalarIterableSupport;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementUnaryOperation;
import org.tensorics.core.tensor.operations.SingleValueTensorCreationOperation;

/**
 * Extends the usage of fields by operations defined on tensors
 * 
 * @author kfuchsbe, agorzaws
 * @param <V> the type of the elements of the tensor(ic)
 */
public class TensorSupport<V> extends ScalarIterableSupport<V> {

    private final Environment<V> environment;

    public TensorSupport(Environment<V> environment) {
        super(environment.field());
        this.environment = environment;
    }

    /**
     * Allows to perform calculation on given tensoric.
     * 
     * @param tensoric to calculate with.
     * @return expression to calculate.
     */
    public final <C> OngoingTensorOperation<C, V> calculate(Tensor<V> tensoric) {
        return new OngoingTensorOperation<>(environment, tensoric);
    }

    /**
     * returns a ZERO value {@link Tensor} for given {@link Shape}.
     * 
     * @param shape to use.
     * @return a {@link Tensor} of given {@link Shape} filled with 0.0;
     */
    public Tensor<V> zeros(Shape shape) {
        return new SingleValueTensorCreationOperation<V>(shape, environment.field().zero()).perform();
    }

    /**
     * returns a IDENTITY value {@link Tensor} for given {@link Shape}.
     * 
     * @param shape to use.
     * @return a {@link Tensor} of given {@link Shape} filled with field identities;
     */
    public Tensor<V> ones(Shape shape) {
        return new SingleValueTensorCreationOperation<V>(shape, environment.field().one()).perform();
    }

    /**
     * @param tensor to use.
     * @return a {@link Tensor} with field inverse values
     */
    public Tensor<V> elementInverseOf(Tensor<V> tensor) {
        return new ElementUnaryOperation<V>(environment.field().multiplicativeInversion()).perform(tensor);
    }

    /**
     * @param tensor to use
     * @return a {@link Tensor} of negative values
     */
    public Tensor<V> negativeOf(Tensor<V> tensor) {
        return new ElementUnaryOperation<V>(environment.field().additiveInversion()).perform(tensor);
    }

    /**
     * Convenience method for {@link TensorStructurals#from(Tensor)}
     * 
     * @param tensor the tensor which shall be manipulated
     * @return a new ongoing manipulation which will allow to extract data.
     */
    public <E1> OngoingTensorManipulation<E1> from(Tensor<E1> tensor) {
        return TensorStructurals.from(tensor);
    }

}
