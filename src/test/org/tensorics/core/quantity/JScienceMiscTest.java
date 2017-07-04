// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.quantity;

import javax.measure.converter.ConversionException;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.Test;

/**
 * @author agorzaws
 */
public class JScienceMiscTest {

	@Test(expected = ConversionException.class)
	public void testMisc() {
		Amount<Length> position = Amount.valueOf(1, 0.02, SI.METER);
		Amount<Length> position2 = Amount.valueOf(1, 0.02, SI.METER);

		Amount<?> positionResult = position.times(position2);
		System.out.println(positionResult);
		System.out.println(positionResult.getAbsoluteError());
		System.out.println(positionResult.getRelativeError());

		position.divide(position2).to(SI.METER);
	}

}
