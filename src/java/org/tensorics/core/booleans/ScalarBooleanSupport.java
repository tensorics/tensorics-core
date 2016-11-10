package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingScalarBooleanAlgebra;

public class ScalarBooleanSupport {

	
	public OngoingScalarBooleanAlgebra calcLogical(Boolean bool){
		return new OngoingScalarBooleanAlgebra(bool);
	}
	
}
