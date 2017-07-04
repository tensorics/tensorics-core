/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.fields.doubles.Structures;

public class TensoricsDoubleExpressionSupport extends TensoricExpressionSupport<Double> {

	public TensoricsDoubleExpressionSupport() {
		super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
	}

}
