package org.tensorics.core.tensor;

/**
 * Marks a tensor, which is a view on one or more other tensor(s). The main purpose of this interface is currently to
 * make the fact of 'viewing' more explicit and let the user reflect on the consequences. (I.g. A tensor is not equal to
 * its own view.)
 * 
 * @author kaifox
 * @param <V> the type of the values of the tensor
 */
public interface TensorView<V> extends Tensor<V> {
    /* Currently only a marker interface */
}
