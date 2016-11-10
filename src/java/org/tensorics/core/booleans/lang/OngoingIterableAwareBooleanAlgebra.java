package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;

public class OngoingIterableAwareBooleanAlgebra {

	private BinaryFunction<Boolean, Boolean> function;
	private Iterable<Boolean> iterableLeft;

	public OngoingIterableAwareBooleanAlgebra(Iterable<Boolean> iterable, BinaryFunction<Boolean, Boolean> function) {
		this.iterableLeft = iterable;
		this.function = function;
	}

	public Iterable<Boolean> with(Iterable<Boolean> iterableRight){
        throw new UnsupportedOperationException("not implemented yet");
	}
	
}
