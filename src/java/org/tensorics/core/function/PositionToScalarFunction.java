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

package org.tensorics.core.function;

import java.util.function.Function;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

import com.google.common.base.Preconditions;

class PositionToScalarFunction<S> implements Function<Position, S> {

	public PositionToScalarFunction(Tensor<S> tensor) {
		super();
		this.tensor = Preconditions.checkNotNull(tensor, "tensor must not be null");
	}

	private final Tensor<S> tensor;

	@Override
	public S apply(Position position) {
		return tensor.get(position);
	}

}