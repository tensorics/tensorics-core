package org.tensorics.core.booleans.lang;

import org.tensorics.core.math.operations.BinaryFunction;

public class OngoingScalarBooleanAlgebra {

	private final Boolean bool;

	public OngoingScalarBooleanAlgebra(Boolean bool) {
		this.bool = bool;
	}

	public OngoingScalarAwareBooleanAlgerbra perform(BinaryFunction<Boolean, Boolean> function) {
		return new OngoingScalarAwareBooleanAlgerbra(bool, function);
	}
}
