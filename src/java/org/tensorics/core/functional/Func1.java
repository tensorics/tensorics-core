/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional;

/**
 * Represents a function with one argument.
 * 
 * @param <T> the first argument type
 * @param <R> the result type
 */
@FunctionalInterface
public interface Func1<T, R> extends FiniteArgumentFunction<R> {
    
    R apply(T t);

    @Override
    @SuppressWarnings("unchecked")
    default FuncN<R> toFuncN() {
        return args -> apply((T) args[0]);
    }

    @Override
    default int numberOfArgs() {
        return 1;
    }
}
