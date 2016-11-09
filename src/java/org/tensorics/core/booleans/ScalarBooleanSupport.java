package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingScalarBooleanAlgebra;

public class ScalarBooleanSupport {

	
	public OngoingScalarBooleanAlgebra on(Boolean bool){
		return new OngoingScalarBooleanAlgebra(bool);
	}
	
}
