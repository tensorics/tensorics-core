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

package org.tensorics.core.tensorbacked;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.tensorics.core.tensor.lang.TensorStructurals.completeWith;
import static org.tensorics.core.tensorbacked.TensorbackedInternals.createBackedByTensor;

import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.TensorInternals;

public class OngoingTensorbackedCompletion<TB extends Tensorbacked<S>, S> {

	private final TB tensorbacked;

	OngoingTensorbackedCompletion(TB tensorbacked) {
		this.tensorbacked = checkNotNull(tensorbacked, "tensorbacked must not be null");
	}

	public TB with(Tensor<S> second) {
		Tensor<S> tensor = completeWith(tensorbacked.tensor(), second);
		return createBackedByTensor(TensorbackedInternals.classOf(tensorbacked), tensor);
	}

	public TB with(TB second) {
		return with(second.tensor());
	}

	public TB with(Shape shape, S value) {
		return with(TensorInternals.sameValues(shape, value));
	}

}
