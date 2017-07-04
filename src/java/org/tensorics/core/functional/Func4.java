/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional;

/**
 * Represents a function with four arguments.
 * 
 * @param <T1>
 *            the first argument type
 * @param <T2>
 *            the second argument type
 * @param <T3>
 *            the third argument type
 * @param <T4>
 *            the fourth argument type
 * @param <R>
 *            the result type
 */
@FunctionalInterface
public interface Func4<T1, T2, T3, T4, R> extends FiniteArgumentFunction<R> {

	R apply(T1 t1, T2 t2, T3 t3, T4 t4);

	@Override
	@SuppressWarnings("unchecked")
	default FuncN<R> toFuncN() {
		return args -> apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3]);
	}

	@Override
	default int numberOfArgs() {
		return 4;
	}
}