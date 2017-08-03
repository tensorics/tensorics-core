// @formatter:off
/**
*
* This file is part of streaming pool (http://www.streamingpool.org).
* 
* Copyright (c) 2017-present, CERN. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
*/
// @formatter:on

package org.tensorics.core.util.names;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import org.junit.Test;
import org.tensorics.core.util.chains.Chains;

public class NamesTest {

    @Test
    public void nonOverriddenStringReturnsNull() {
        Function<Object, String> chain = Chains.chainFor(String.class).or(Names::fromOverriddenToString).orElseNull();
        String name = chain.apply(new EmptyTestClass());
        assertThat(name).isNull();
    }

    @Test
    public void chainOverriddenToStringWithSimpleClassNameReturnsSimpleClassName() {
        Function<Object, String> chain = Chains.chainFor(String.class).or(Names::fromOverriddenToString)
                .or(Names::fromSimpleClassName).orElseNull();
        String name = chain.apply(new EmptyTestClass());
        assertThat(name).isEqualTo("EmptyTestClass");
    }

    @Test
    public void anyToStringWillNotBeCalledWhenSimpleClassNameBefore() {
        Function<Object, String> chain = Chains.chainFor(String.class).or(Names::fromOverriddenToString)
                .or(Names::fromSimpleClassName).or(Names::fromToString).orElseNull();
        String name = chain.apply(new EmptyTestClass());
        assertThat(name).isEqualTo("EmptyTestClass");
    }

    private static final class EmptyTestClass {
        /* empty on purpose */
    }
}
