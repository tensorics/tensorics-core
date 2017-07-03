package org.tensorics.core.tensor;

/**
 * Enhances the tensor interface by a method to view the tensor as a map from
 * position to values. This interface is intended to be used by utility methods
 * in order to avoid unnecessary calls.
 * 
 * @author kaifox
 *
 * @param <V>
 *            the type of the values of the tensors
 */
public interface MapableTensor<V> extends Tensor<V> {

}
