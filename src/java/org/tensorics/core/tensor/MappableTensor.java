package org.tensorics.core.tensor;

import java.util.Map;

/**
 * Enhances the tensor interface by a method to view the tensor as a map from position to values. This interface is
 * intended to be used by utility methods in order to avoid unnecessary calls.
 * 
 * @author kaifox
 * @param <V> the type of the values of the tensors
 */
public interface MappableTensor<V> extends Tensor<V> {

    /**
     * By implementing this method, a tensor can provide an efficient way to convert the tensor to a map from position
     * to values. Implementing instances must follow the following contract:
     * <ul>
     * <li>this method must not return {@code null}
     * <li>It is recommended to return either a copy or an immutable map. Despite clients are not supposed to manipulate
     * the returned maps, it cannot be guaranteed and this could lead to unexpected results.
     * </ul>
     * Usually, clients will not use this method directly, but should use
     * {@link org.tensorics.core.tensor.operations.TensorInternals#mapFrom(Tensor)} (internal clients) or
     * {@link org.tensorics.core.lang.Tensorics#mapFrom(Tensor)} (external clients)
     * 
     * @see org.tensorics.core.tensor.operations.TensorInternals#mapFrom(Tensor)
     * @see org.tensorics.core.lang.Tensorics#mapFrom(Tensor)
     * @return a (preferrably immutable) map representing the content of the tensor
     */
    public Map<Position, V> asMap();

}
