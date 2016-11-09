package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;

public class OngoingScalarAwareBooleanAlgerbra {

	private final Boolean bool;
	private final BinaryFunction<Boolean, Boolean> function;

	public OngoingScalarAwareBooleanAlgerbra(Boolean bool, BinaryFunction<Boolean, Boolean> function) {
		this.bool = bool;
		this.function = function;
	}

	public Boolean with(Boolean bool2) {
		return function.perform(bool, bool2);
	}

}
