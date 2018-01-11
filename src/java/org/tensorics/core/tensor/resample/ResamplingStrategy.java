package org.tensorics.core.tensor.resample;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

/**
 * Defines the behaviour, how tensors can be 'resampled'. To 'resample' a tensor means that it might afterwards be valid
 * for a broader (or tighter) position-set than originally. Examples are, e.g. to interpolate values or to just repeat
 * some. <p> Note: The dimensionality of a tensor (the the number and types of its coordinate are preserved)
 * 
 * @author kaifox
 */
public interface ResamplingStrategy {

    /* to be seen here what we need.. ;-) This is a guess for the moment: */
    <V> V resample(Tensor<V> originalTensor, Position position);

}
