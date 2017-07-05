/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional;

/**
 * Represents a vector-argument function.
 * 
 * @param <R> the result type
 */
@FunctionalInterface
public interface FuncN<R> {
    R apply(Object... args);
}