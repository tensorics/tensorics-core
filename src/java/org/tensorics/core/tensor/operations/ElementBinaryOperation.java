/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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
 * <li> {@link BroadcastingStrategy}
 * <li> {@link ShapingStrategy}
 * </ul>
 * 
 * @author kfuchsbe
 * @param <V> The type of the tensor values
 */
public class ElementBinaryOperation<V> extends ElementBinaryFunction<V, V> implements BinaryOperation<Tensor<V>> {

    public ElementBinaryOperation(BinaryOperation<V> operation, OptionRegistry<ManipulationOption> optionRegistry) {
        super(operation, optionRegistry);
    }

}
