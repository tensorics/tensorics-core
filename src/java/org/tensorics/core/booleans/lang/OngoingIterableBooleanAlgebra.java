package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;

public class OngoingIterableBooleanAlgebra {

	private Iterable<Boolean> iterable;

	public OngoingIterableBooleanAlgebra(Iterable<Boolean> iterable) {
		this.iterable = iterable;

	}

	public OngoingIterableAwareBooleanAlgebra perform(BinaryFunction<Boolean, Boolean> function) {
		return new OngoingIterableAwareBooleanAlgebra(iterable, function);
	}

}
