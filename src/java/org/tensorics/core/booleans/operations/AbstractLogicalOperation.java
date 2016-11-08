/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.operations;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.options.ExactShapesOrOneZeroStrategy;
import org.tensorics.core.tensor.options.ShapingStrategy;

public abstract class AbstractLogicalOperation implements LogicalOperation {

    /**
     * An unary boolean definition
     * 
     * @param left
     * @param right
     * @return
     */
    public abstract Boolean getResult(Boolean left, Boolean right);

    @Override
    public Tensor<Boolean> perform(Tensor<Boolean> left, Tensor<Boolean> right) {
        return perform(left, right, ExactShapesOrOneZeroStrategy.getInstance());
    }

    @Override
    public Tensor<Boolean> perform(Tensor<Boolean> tensor, Tensor<Boolean> tensor2, ShapingStrategy shapingStrategy) {
        Shape shape = checkShapesAndGetOne(tensor, tensor, shapingStrategy);
        TensorBuilder<Boolean> builder = Tensorics.builder(shape.dimensionSet());
        for (Position one : shape.positionSet()) {
            builder.putAt(getResult(tensor.get(one), tensor2.get(one)), one);
        }
        return builder.build();
    }

    private Shape checkShapesAndGetOne(Tensor<Boolean> tensor, Tensor<Boolean> tensor2,
            ShapingStrategy shapingStrategy) {
        return shapingStrategy.shapeLeftRight(tensor, tensor2);
    }

}
