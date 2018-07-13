/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.resample.impl;

import static org.junit.Assert.*;
import static org.tensorics.core.lang.TensoricDoubles.resample;
import static org.tensorics.core.lang.Tensorics.at;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.core.lang.TensoricDoubles;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.Tensoric;

public class LinearInterpolationResamplerTest {

    // @formatter:off
    /*
     * This is a simple matrix for testing, which has two dimensions: 
     * Integer.class and String.class.
     * 
     * It contains four values of type double:
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |  
     *    dim :    B | 1.0 2.0
     *             C |
     *             D |             6.0 5.0
     */
    private Tensor<Double> original = ImmutableTensor.<Double> builder(String.class, Integer.class)
            .put(at("B", 1), 1.0)
            .put(at("B", 2), 2.0)
            .put(at("D", 4), 4.0)
            .put(at("D", 5), 3.0)
            .build();
    // @formatter:on

    // @formatter:off
    /*
     * By only linear interpolation in Integer direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A |  
     *    dim :    B | 1.0 2.0 3.0 4.0 5.0
     *             C |
     *             D | 9.0 8.0 7.0 6.0 5.0
     */
    private Tensoric<Double> linearInt = resample(original)
            .linear(Integer.class, Integer::doubleValue)
            .toTensoric();
    // @formatter:on

    
    // @formatter:off
    /*
     * By linear interpolation in Integer direction and then repeating in String direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A | 1.0 2.0 3.0 4.0 5.0 
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

    
    // @formatter:off
    /*
     * By repeating in String direction and then linear interpolation in Integer direction, we expect: 
     * 
     * \ Integer dim:|
     *  \____________|__1___2___3___4___5_...
     * String      A | 1.0 2.0 4.0 6.0 5.0
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
    public void test() {
        Assertions.assertThat(true).isTrue();
    }

}
