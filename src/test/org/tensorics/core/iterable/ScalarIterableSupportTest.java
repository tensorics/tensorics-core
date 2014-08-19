/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable;

import static org.junit.Assert.assertEquals;
import static org.tensorics.core.fields.doubles.Structures.doubles;

import java.util.Collections;

import org.junit.Test;
import org.tensorics.core.iterable.lang.ScalarIterableSupport;

import com.google.common.collect.ImmutableList;

/**
 * @author kfuchsbe
 */
public class ScalarIterableSupportTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoElement() {
        usage().avarageOf(Collections.<Double> emptyList());
    }

    private ScalarIterableSupport<Double> usage() {
        return new ScalarIterableSupport<>(doubles());
    }
 
    @Test
    public void testOneElementOne() {
        assertEquals(1.0, usage().avarageOf(ImmutableList.<Double> of(1.0)), 0.000001);
    }

    @Test
    public void testOneElementZero() {
        assertEquals(0.0, usage().avarageOf(ImmutableList.<Double> of(0.0)), 0.0000001);
    }

    @Test
    public void testTwoElements() throws Exception {
        assertEquals(0.5, usage().avarageOf(ImmutableList.<Double> of(0.0, 1.0)), 0.00001);
    }
}
