package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingBooleanScalarOperation;

public class ScalarBooleanSupport {

	public OngoingBooleanScalarOperation calcLogical(Boolean left){
		return new OngoingBooleanScalarOperation(left);
	}
	
}
