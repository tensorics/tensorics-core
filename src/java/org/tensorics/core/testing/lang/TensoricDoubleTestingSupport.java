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

package org.tensorics.core.testing.lang;

import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;

/**
 * This class is the specialization to provide methods which deal with tensorics
 * of double values. This class is intended to be inherited from.
 * 
 * @author kfuchsbe
 */
public class TensoricDoubleTestingSupport extends TensoricTestingSupport<Double> {

	public TensoricDoubleTestingSupport() {
		super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
	}

}
