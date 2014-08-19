package org.tensorics.core.math.operations;

/**
 * Represents an operation, which needs no arguments and creates an object of a certain type
 * 
 * @author kaifox
 * @param <T> the type of the object to be created by this operation
 */
public interface CreationOperation<T> {

    /**
     * Performs the creation
     * 
     * @return a newly created value of the specified type
     */
    T perform();
}
