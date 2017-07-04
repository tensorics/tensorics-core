/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function;

import org.junit.Before;
import org.junit.Ignore;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.TensorBuilder;

/**
 * Extension of {@link AbstractDiscreteFunctionVs1DTensorTest} that focuses on
 * testing 1D tensors which have NO coordinates in common.
 * 
 * @author caguiler
 */
@Ignore
public class DiscreteFunctionVs1DTensorWithNoCommonCoordinatesTest extends AbstractDiscreteFunctionVs1DTensorTest {

	@Before
	public void setUp() {
		TensorBuilder<Double> builder2 = Tensorics.builder(Double.class);
		TensorBuilder<Double> builder3 = Tensorics.builder(Double.class);

		for (double i = 1; i <= 10; ++i) {
			if (i % 2 == 0) {
				builder2.putAt(2.0, i);
			} else {
				builder3.putAt(3.0, i);
			}
		}

		two = builder2.build();
		three = builder3.build();
	}
}
