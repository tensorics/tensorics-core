/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.tensor.lang.TensorStructurals.resample;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

/**
 * Test for the mixed resampling strategy by using repeating in the individual dimenstions
 * 
 * @author kfuchsbe
 */
public class MultiDimensionalResamplingTest {

    // @formatter:off
    /*
     * This is a simple matrix for testing, which has two dimensions: 
     * Integer.class and String.class.
     * 
     * It contains two values of type String:
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |  
     *    dim :    B |     B2
     *             C |
     *             D |             D4
     */
    private Tensor<String> b2d4 = ImmutableTensor.<String> builder(String.class, Integer.class)
            .put(at("B", 2), "B2")
            .put(at("D", 4), "D4")
            .build();
    // @formatter:on

    // @formatter:off
    /*
     * This resamples the tensor by repeating first in the String and then in the
     * Integer dimension (Forward if possible, otherwise backward). This results in:
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A | B2  B2  B2  D4  D4
     *    dim :    B | B2  B2  B2  D4  D4
     *             C | B2  B2  B2  D4  D4
     *             D | B2  B2  B2  D4  D4
     */
    // @formatter:on
    private Tensoric<String> stringThenInt = resample(b2d4).repeat(String.class).then().repeat(Integer.class)
            .toTensoric();

    @Test
    public void stringThenIntResamplesD3sB2() {
        assertThat(stringThenInt.get("D", 3)).isEqualTo("B2");
    }

    @Test
    public void stringThenIntResamplesD5AsD4() throws Exception {
        assertThat(stringThenInt.get("D", 5)).isEqualTo("D4");
    }

    @Test
    public void stringThenIntResamplesA10AsD4() throws Exception {
        assertThat(stringThenInt.get("A", 10)).isEqualTo("D4");
    }

    // @formatter:on// @formatter:off
    /*
     * This resamples the tensor by repeating first in the Integer and then in the
     * String dimension (Forward if possible, otherwise backward). This results in:
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5
     * String      A | B2  B2  B2  B2  B2
     *    dim :    B | B2  B2  B2  B2  B2
     *             C | D4  D4  D4  D4  D4
     *             D | D4  D4  D4  D4  D4
     */
    // @formatter:on
    private Tensoric<String> intThenString = resample(b2d4).repeat(Integer.class).then().repeat(String.class)
            .toTensoric();

    @Test
    public void intThenStringResamplesD3sD4() {
        assertThat(intThenString.get("D", 3)).isEqualTo("D4");
    }

    @Test
    public void intThenStringResamplesD5AsD4() {
        assertThat(intThenString.get("D", 5)).isEqualTo("D4");
    }

    @Test
    public void intThenStringResamplesA10AsB2() {
        assertThat(intThenString.get("A", 10)).isEqualTo("B2");
    }

    // @formatter:off
    /*
     * This resamples the tensor only by repeating in string dimension 
     * (Forward if possible, otherwise backward). This results in:
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |     B2      D4  
     *    dim :    B |     B2      D4  
     *             C |     B2      D4  
     *             D |     B2      D4  
     */
    // @formatter:on
    private Tensoric<String> stringOnly = resample(b2d4).repeat(String.class).toTensoric();

    @Test
    public void stringOnlyResamplesC4AsD4() {
        assertThat(stringOnly.get("C", 4)).isEqualTo("D4");
    }

    @Test
    public void stringOnlyResamplesE4AsD4() {
        assertThat(stringOnly.get("E", 4)).isEqualTo("D4");
    }

    @Test(expected = NoSuchElementException.class)
    public void stringOnlyDoesNotResampleA10() {
        stringOnly.get("A", 10);
    }

}
