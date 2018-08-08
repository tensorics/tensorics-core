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

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.BroadcastingStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

/**
 * A binary operations on tensors which can be decoupled into simple operations on an element of one tensor on the
 * corresponding element on the second tensor (same position). Because of this simple relation, the operation is fully
 * defined by a binary operation on individual tensor values.
 * <p>
 * To decide on the resulting shape of the tensor, two strategies are used here:
 * <ul>
 * <li>{@link BroadcastingStrategy}
 * <li>{@link ShapingStrategy}
 * </ul>
 *
 * @author kfuchsbe
 * @param <V> The type of the tensor values
 */
public class ElementBinaryOperation<V> extends ElementBinaryFunction<V, V> implements BinaryOperation<Tensor<V>> {
    private static final long serialVersionUID = 1L;

    public ElementBinaryOperation(BinaryOperation<V> operation, OptionRegistry<ManipulationOption> optionRegistry) {
        super(operation, optionRegistry);
    }

}
