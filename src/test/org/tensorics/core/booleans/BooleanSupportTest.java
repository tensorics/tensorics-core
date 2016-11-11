/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.lang.ManipulationOptions;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensor.options.IntersectionShapingStrategy;

public class BooleanSupportTest extends BooleanSupport {

    private static final IntersectionShapingStrategy NON_DEFAULT_SHAPING = IntersectionShapingStrategy.get();

    private static final OptionRegistry<ManipulationOption> DEFAULT_REGISTRY = ManipulationOptions
            .defaultStructuralOnly();

    public BooleanSupportTest() {
        super(DEFAULT_REGISTRY);
    }

    private static final int CHANGE_OF_THE_SIGNAL = 51;

    @Test
    public void testBooleanScalarAlgebra() {
        Boolean with = calcLogical(true).and(false);
        assertFalse(with);
    }

    /**
     * TODO implement the operation for iterables
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testBooleanIterableAlgebra() {
        Boolean[] test1 = new Boolean[] { true, true, false };
        Boolean[] test2 = new Boolean[] { true, true, false };
        calcLogical(Arrays.asList(test1)).and(Arrays.asList(test2));
    }

    @Test
    public void testBooleanTensorAlgebraJustCalc() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 6, 1);
        Tensor<Boolean> tensorFalse = createSimpleOneComparableDimensionTensorOf(false, 6, 1);
        Tensor<Boolean> tensorFalseOther = createSimpleOneComparableDimensionTensorOf(false, 6, 2);

        /* few dsl flows are actually possible (and not contradicting!) */

        /* one way */
        Tensor<Boolean> resultAND2 = calcLogical(tensorTrue).and(tensorTrue);
        Tensor<Boolean> resultANDWith = with(NON_DEFAULT_SHAPING).calcLogical(tensorTrue).and(tensorTrue);

        /* other way */
        Tensor<Boolean> resultANDOther = calcLogical(tensorTrue).and(tensorTrue);
        Tensor<Boolean> resultANDOther2 = with(NON_DEFAULT_SHAPING).calcLogical(tensorTrue).and(tensorTrue);

        /* etc */

        Tensor<Boolean> resultOR = calcLogical(tensorTrue).or(tensorFalse);
        Tensor<Boolean> resultXOR = calcLogical(tensorFalseOther).xor(tensorFalse);
    }

    @Test
    public void testSetDifferentStrategy() {
        Tensor<Boolean> tensorFalse = createSimpleOneComparableDimensionTensorOf(false, 6, 1);
        Tensor<Boolean> tensorFalseOther = createSimpleOneComparableDimensionTensorOf(false, 6, 2);
        /* explicit shaping strategy to apply but */
        with(NON_DEFAULT_SHAPING).calcLogical(tensorFalseOther).and(tensorFalse);
    }

    @Test
    public void testChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 1);
        Tensor<Boolean> result = calcLogical(tensorTrue).and(tensorTrue);
        System.out.println(result);
        Iterable<Integer> changes = detectWhere(tensorTrue).changesAlong(Integer.class);
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(CHANGE_OF_THE_SIGNAL));
    }

    @Test
    public void testMoreChangesDetectionInDirection() {
        Tensor<Boolean> tensorTrue = createSimpleOneComparableDimensionTensorOf(true, 100, 10);
        Tensor<Boolean> result = calcLogical(tensorTrue).and(tensorTrue);

        System.out.println(result);
        Iterable<Integer> changes = detectWhere(tensorTrue).changesAlong(Integer.class);
        System.out.println(changes);
        assertTrue(changes.iterator().hasNext());
        assertTrue(changes.iterator().next().equals(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDirection() {
        Tensor<Boolean> tensor = createSimpleOneComparableDimensionTensorOf(true, 2, 1);
        @SuppressWarnings("unused")
        Iterable<Double> changes2 = detectWhere(tensor).changesAlong(Double.class);
        // Iterable<String> changes = detectWhere(tensor).changes().inDirectionOf(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooLargeTensor() {
        Tensor<Boolean> tensorTrue = createTensorOf(true);
        @SuppressWarnings("unused")
        Iterable<Integer> changes2 = detectWhere(tensorTrue).changesAlong(Integer.class);
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
