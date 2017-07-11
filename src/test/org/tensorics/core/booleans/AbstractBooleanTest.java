/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.tensorics.core.lang.Tensorics.at;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.options.IntersectionShapingStrategy;

public abstract class AbstractBooleanTest extends BooleanSupport {

    protected static final IntersectionShapingStrategy NON_DEFAULT_SHAPING = IntersectionShapingStrategy.get();

    protected static final int CHANGE_OF_THE_SIGNAL = 51;

    public AbstractBooleanTest() {
        super();
    }

    protected static Tensor<Boolean> createTensorOf(boolean b, int stringDimensionSize, int integerDimensionSize) {
        TensorBuilder<Boolean> builder = Tensorics.builder(String.class, Integer.class);
        for (int i = 0; i < integerDimensionSize; i++) {
            for (int j = 1; j < stringDimensionSize; j++) {
                boolean toPut = (i > 5 ? b : !b);
                builder.put(Tensorics.at(i, new String(j + "_test")), toPut);
            }
        }
        return builder.build();
    }

    protected static Tensor<Boolean> createSimpleOneComparableDimensionTensorOf(boolean b, int totalNbOfSamples,
            int nbOfChanges) {
        if (nbOfChanges > totalNbOfSamples) {
            throw new IllegalArgumentException("nb of changes cannot be larger than total samples");
        }
        TensorBuilder<Boolean> builder = Tensorics.builder(Integer.class);
        if (nbOfChanges < 2) {
            for (int i = 1; i < totalNbOfSamples; i++) {
                boolean toPut = (i > (CHANGE_OF_THE_SIGNAL - 1) ? b : !b);
                builder.put(at(i), toPut);
            }
        } else {
            int changePeriod = totalNbOfSamples / nbOfChanges;
            int count = 0;
            for (int i = 1; i < totalNbOfSamples; i++) {
                if (i > changePeriod * count) {
                    count++;
                }
                boolean toPut = (count % 2 == 0 ? b : !b);
                builder.put(at(i), toPut);
            }
        }
        return builder.build();
    }

}
