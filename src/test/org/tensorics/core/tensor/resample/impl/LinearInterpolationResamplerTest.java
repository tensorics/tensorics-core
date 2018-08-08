/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.tensorics.core.lang.TensoricDoubles.resample;
import static org.tensorics.core.lang.Tensorics.at;

import java.util.NoSuchElementException;

import org.assertj.core.data.Offset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

/**
 * Acceptance test for both, the general combined resampling mechanism and the repeating and linear interpolation
 * resamplers. It uses the starting point for the fluent clause of the FieldSupport
 * {@code TensoricDoubles#resample(Tensor)} and explores the different corner cases of a combined resampling.
 * 
 * @author kfuchsbe
 */
public class LinearInterpolationResamplerTest {

    private static final Offset<Double> DEFAULT_OFFSET = offset(1.0e-6);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // @formatter:off
    /*
     * This is a simple matrix for testing, which has two dimensions: 
     * Integer.class and String.class.
     * 
     * It contains five values of type double:
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |     1.0
     *    dim :    B | 1.0 2.0
     *             C |
     *             D |             6.0 5.0
     */
    private Tensor<Double> original = ImmutableTensor.<Double> builder(String.class, Integer.class)
            .put(at("A", 2), 1.0)
            .put(at("B", 1), 1.0)
            .put(at("B", 2), 2.0)
            .put(at("D", 4), 6.0)
            .put(at("D", 5), 5.0)
            .build();
    // @formatter:on

    // @formatter:off
    /*
     * By only linear interpolation in Integer direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |     1.0
     *    dim :    B | 1.0 2.0 3.0 4.0 5.0
     *             C |
     *             D | 9.0 8.0 7.0 6.0 5.0
     */
    private Tensoric<Double> linearIntOnly = resample(original)
            .linear(Integer.class, Integer::doubleValue)
            .toTensoric();
    // @formatter:on

    @Test
    public void linearIntOnlyC2Throws() {
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("cannot be resampled");

        linearIntOnly.get("C", 2);
    }

    @Test
    public void linearIntOnlyA3Throws() {
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("cannot be resampled");

        linearIntOnly.get("A", 3);
    }

    @Test
    public void linearIntOnlyA2ResamplesToSupportPoint() {
        assertThat(linearIntOnly.get("A", 2)).isCloseTo(1.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntOnlyB2ResamplesToSupportPoint() {
        assertThat(linearIntOnly.get("B", 2)).isCloseTo(2.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntOnlyB4IsResampledFromB1B2() {
        assertThat(linearIntOnly.get("B", 4)).isCloseTo(4.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntOnlyD1IsResampledFromD4D5() {
        assertThat(linearIntOnly.get("D", 1)).isCloseTo(9.0, DEFAULT_OFFSET);
    }

    // @formatter:off
    /*
     * By linear interpolation in Integer direction and then repeating in String direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A | 1.0 1.0 3.0 4.0 5.0 
     *    dim :    B | 1.0 2.0 3.0 4.0 5.0
     *             C | 1.0 2.0 3.0 4.0 5.0
     *             D | 9.0 8.0 7.0 6.0 5.0
     *             E | 9.0 8.0 7.0 6.0 5.0
     */
    private Tensoric<Double> linearIntThenRepeatString = resample(original)
            .linear(Integer.class, Integer::doubleValue)
            .then().repeat(String.class)
            .toTensoric();
    // @formatter:on

    @Test
    public void linearIntThenRepeatStringA4IsResampledFromB1B2() {
        assertThat(linearIntThenRepeatString.get("A", 4)).isCloseTo(4.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntThenRepeatStringA2ResamplesToSupportPoint() {
        assertThat(linearIntThenRepeatString.get("A", 2)).isCloseTo(1.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntThenRepeatStringC3IsResampledFromB1B2() {
        assertThat(linearIntThenRepeatString.get("C", 3)).isCloseTo(3.0, DEFAULT_OFFSET);
    }

    @Test
    public void linearIntThenRepeatStringE2IsResampledFromD4D5() {
        assertThat(linearIntThenRepeatString.get("E", 2)).isCloseTo(8.0, DEFAULT_OFFSET);
    }

    // @formatter:off
    /*
     * By repeating in String direction and then linear interpolation in Integer direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A | 1.0 1.0 3.5 6.0 5.0
     *    dim :    B | 1.0 2.0 4.0 6.0 5.0
     *             C | 1.0 2.0 4.0 6.0 5.0
     *             D | 1.0 2.0 4.0 6.0 5.0
     *             E | 1.0 2.0 4.0 6.0 5.0
     */
    private Tensoric<Double> repeatStringThenLinearInteger= resample(original)
            .repeat(String.class)
            .then().linear(Integer.class, Integer::doubleValue)
            .toTensoric();
    // @formatter:on

    @Test
    public void repeatStringThenLinearIntegerA3IsResampledFromA2D4() {
        assertThat(repeatStringThenLinearInteger.get("A", 3)).isCloseTo(3.5, DEFAULT_OFFSET);
    }

    @Test
    public void repeatStringThenLinearIntegerC3IsResampledFromB2D4() {
        assertThat(repeatStringThenLinearInteger.get("C", 3)).isCloseTo(4.0, DEFAULT_OFFSET);
    }
    
    @Test
    public void repeatStringThenLinearIntegerE2IsResampledFromB2() {
        assertThat(repeatStringThenLinearInteger.get("E", 2)).isCloseTo(2.0, DEFAULT_OFFSET);
    }

}
