/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.options.ExactShapesOrOneZeroStrategy;

public class BooleanTensorSupoportTest extends BooleanTensorSupoport {

    private static final int CHANGE_OF_THE_SIGNAL = 51;

    @Test
    public void testBooleanAlgebraJustOutput() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 6, 1);
        Tensor<Boolean> tensorFalse = createSimpleOneComparableDimensionTensorOf(false, 6, 1);
        Tensor<Boolean> tensorFalseOther = createSimpleOneComparableDimensionTensorOf(false, 6, 2);

        /* default shaping strategy to apply */
        Tensor<Boolean> resultAND = on(tensorTrue).apply(AND).with(tensorFalse);
        Tensor<Boolean> resultOR = on(tensorTrue).apply(OR).with(tensorFalse);
        Tensor<Boolean> resultXOR = on(tensorFalseOther).apply(XOR).with(tensorFalse);

        System.out.println(resultAND);
        System.out.println(resultOR);
        System.out.println(resultXOR);

        /* explicit shaping strategy to apply */
        on(tensorFalseOther).apply(AND).withShaping(ExactShapesOrOneZeroStrategy.getInstance()).with(tensorFalse);
    }

    @Test
    public void testChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 1);
        Tensor<Boolean> result = on(tensorTrue).apply(AND).with(tensorTrue);
        System.out.println(result);
        Iterable<Integer> changes = detect().inDirectionOf(Integer.class).where(tensorTrue).changes();
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(CHANGE_OF_THE_SIGNAL));
    }

    @Test
    public void testMoreChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 10);
        Tensor<Boolean> result = on(tensorTrue).apply(AND).with(tensorTrue);
        System.out.println(result);
        Iterable<Integer> changes = detect().inDirectionOf(Integer.class).where(tensorTrue).changes();
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 2, 1);
        @SuppressWarnings("unused")
        Iterable<Double> changes2 = detect().inDirectionOf(Double.class).where(tensorTrue).changes();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLargeTensor() {
        Tensor<Boolean> tensorTrue = createTensorOf(true);
        @SuppressWarnings("unused")
        Iterable<Integer> changes2 = detect().inDirectionOf(Integer.class).where(tensorTrue).changes();
    }

    private static Tensor<Boolean> createTensorOf(boolean b) {
        TensorBuilder<Boolean> builder = Tensorics.builder(String.class, Integer.class);
        for (int i = 0; i < 12; i++) {
            for (int j = 1; j < 3; j++) {
                boolean toPut = (i > 5 ? b : !b);
                builder.putAt(toPut, i, new String(j + "_test"));
            }
        }
        return builder.build();
    }

    private static Tensor<Boolean> createSimpleOneComparableDimensionTensorOf(boolean b, int totalNbOfSamples,
            int nbOfChanges) {
        if (nbOfChanges > totalNbOfSamples) {
            throw new IllegalArgumentException("nb of changes cannot be larger than total samples");
        }
        TensorBuilder<Boolean> builder = Tensorics.builder(Integer.class);
        if (nbOfChanges < 2) {
            for (int i = 1; i < totalNbOfSamples; i++) {
                boolean toPut = (i > (CHANGE_OF_THE_SIGNAL - 1) ? b : !b);
                builder.putAt(toPut, i);
            }
        } else {
            int changePeriod = totalNbOfSamples / nbOfChanges;
            int count = 0;
            for (int i = 1; i < totalNbOfSamples; i++) {
                if (i > changePeriod * count) {
                    count++;
                }
                boolean toPut = (count % 2 == 0 ? b : !b);
                builder.putAt(toPut, i);
            }
        }
        return builder.build();
    }

}
