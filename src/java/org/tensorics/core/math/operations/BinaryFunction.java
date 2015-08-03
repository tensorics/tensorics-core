package org.tensorics.core.math.operations;

public interface BinaryFunction<T, R> {

    
    /**
     * Has to be implemented to perform the actual operation. The order of operands might be important or not,
     * depending, if the operation is commutative or not.
     * 
     * @param left the left operand to be used in the operation
     * @param right the right operand to be used in the operation
     * @return the result of the operation
     */
    R perform(T left, T right);

}
