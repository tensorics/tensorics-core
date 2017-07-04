/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.functional;

/**
 * Represents a function with six arguments.
 * 
 * @param <T1>
 *            the first argument type
 * @param <T2>
 *            the second argument type
 * @param <T3>
 *            the third argument type
 * @param <T4>
 *            the fourth argument type
 * @param <T5>
 *            the fifth argument type
 * @param <T6>
 *            the sixth argument type
 * @param <R>
 *            the result type
 */
@FunctionalInterface
public interface Func6<T1, T2, T3, T4, T5, T6, R> extends FiniteArgumentFunction<R> {

	R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

	@Override
	@SuppressWarnings("unchecked")
	default FuncN<R> toFuncN() {
		return args -> apply((T1) args[0], (T2) args[1], (T3) args[2], (T4) args[3], (T5) args[4], (T6) args[5]);
	}

	@Override
	default int numberOfArgs() {
		return 6;
	}
}