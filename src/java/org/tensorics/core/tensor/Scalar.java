package org.tensorics.core.tensor;

/**
 * The specialization of a tensor with zero dimensions and exactly one value. In detail, implementations have to
 * guarantee the following:
 * <ul>
 * <li>Has exactly one value (not zero, no more)
 * <li>has always zero dimensions
 * <li>The only valid position to query is the 'empty' position
 * </ul>
 * Still, a scalar can have a context. Indeed, this is a very valuable property in the framework of tensorics.
 * <p>
 * NOTE: It might sometimes be confusing, that in mathematics and commons language the term 'scalar' is sometimes used
 * interchangible for a zero-dimensional tensor (what this class represents) and the values/entries of the tensors
 * itself. Therefore, we will denote the values of a tensor (scalar) as 'values' and zero-dimensional tensors as
 * scalars.
 * 
 * @author kaifox
 * @param <V> the type of the value of the scalar
 */
public interface Scalar<V> extends Tensor<V> {

    /**
     * Has to retrieve the value of the scalar. In implementations, this has to be equivalent to calling
     * {@link #get(Object...)} with an empty array of coordinates and with calling {@link #get(Position)} with an empty
     * position.
     * 
     * @return the internal value of the scalar
     */
    public V value();

}
