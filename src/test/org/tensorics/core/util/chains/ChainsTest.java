/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;

import org.junit.Test;

public class ChainsTest {

    @Test
    public void test() {
        Chain<String> chain = Chains.chainFor(String.class).either((a) -> Objects.toString(a)).orElseThrow();

        assertThat(chain.apply(Integer.valueOf(5))).isEqualTo("5");

        int endRecursionDepth = 4;
        assertThat(chain.apply(Integer.valueOf(10), endRecursionDepth)).isEqualTo("10");
    }

}
