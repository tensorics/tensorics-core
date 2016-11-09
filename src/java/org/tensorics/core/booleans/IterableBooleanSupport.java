package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingIterableBooleanAlgebra;

public class IterableBooleanSupport {

	public OngoingIterableBooleanAlgebra on(Iterable<Boolean> iterable){
		return new OngoingIterableBooleanAlgebra(iterable);
	}
	
}
