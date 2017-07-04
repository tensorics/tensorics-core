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
package org.tensorics.core.tensor.operations;

import java.util.Collections;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorPair;
import org.tensorics.core.tensor.options.BroadcastingStrategy;
import org.tensorics.core.tensor.options.ContextPropagationStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

public class ElementBinaryFunction<V, R> implements BinaryFunction<Tensor<V>, Tensor<R>> {

	protected final BinaryFunction<V, R> operation;
	protected final OptionRegistry<ManipulationOption> optionRegistry;

	public ElementBinaryFunction(BinaryFunction<V, R> operation, OptionRegistry<ManipulationOption> optionRegistry) {
		super();
		this.operation = operation;
		this.optionRegistry = optionRegistry;
	}

	@Override
	public Tensor<R> perform(Tensor<V> left, Tensor<V> right) {
		TensorPair<V> broadcastedPair = broadcast(left, right);
		Shape resultingShape = shape(broadcastedPair);
		Position resultingContext = contextLeftRight(left, right);
		return performOperation(broadcastedPair.left(), broadcastedPair.right(), resultingShape, resultingContext);
	}

	private Position contextLeftRight(Tensor<V> left, Tensor<V> right) {
		ContextPropagationStrategy strategy = optionRegistry.get(ContextPropagationStrategy.class);
		return strategy.contextForLeftRight(left.context(), right.context());
	}

    private Tensor<R> performOperation(Tensor<V> left, Tensor<V> right, Shape resultingShape, Position resultingContext) {
        Builder<R> tensorBuilder = ImmutableTensor.builder(resultingShape.dimensionSet());
        for (Position position : resultingShape.positionSet()) {
            tensorBuilder.put(position, operation.perform(left.get(position), right.get(position)));
        }
        tensorBuilder.context(resultingContext);
        return tensorBuilder.build();
    }

	private Shape shape(TensorPair<V> broadcasted) {
		ShapingStrategy strategy = optionRegistry.get(ShapingStrategy.class);
		return strategy.shapeLeftRight(broadcasted.left(), broadcasted.right());
	}

	private TensorPair<V> broadcast(Tensor<V> left, Tensor<V> right) {
		BroadcastingStrategy broadcastingStrategy = optionRegistry.get(BroadcastingStrategy.class);
		return broadcastingStrategy.broadcast(left, right, Collections.<Class<?>>emptySet());
	}

}