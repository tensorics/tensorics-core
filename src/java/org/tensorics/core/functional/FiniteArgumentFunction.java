/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional;

/**
 * This interface represents a function that has a finite argument number in its signature.
 * 
 * @see Func1
 * @see Func2
 * @see Func3
 * @see Func4
 * @see Func5
 * @see Func6
 * @see Func7
 * @see Func8
 * @see Func9
 * @param <R> the return type of the function
 */
public interface FiniteArgumentFunction<R> {

    /**
     * Transform this function to a {@link FuncN}, binding the arguments automatically.
     * 
     * @return the {@link FuncN} representation of this function
     */
    FuncN<R> toFuncN();

    /**
     * The number of arguments that the function accepts
     * 
     * @return the number of arguments
     */
    int numberOfArgs();
}
