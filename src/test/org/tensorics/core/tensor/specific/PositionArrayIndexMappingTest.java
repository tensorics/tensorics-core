// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
 ******************************************************************************/
// @formatter:on
package org.tensorics.core.tensor.specific;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.specific.PositionIndexer.Builder;

import com.google.common.collect.ImmutableSet;

public class PositionArrayIndexMappingTest {

    private static final ImmutableSet<Long> LONGS = ImmutableSet.of(5L, 7L);
    private static final ImmutableSet<Integer> INTS = ImmutableSet.of(1, 2);
    private static final ImmutableSet<String> STRINGS = ImmutableSet.of("A", "B", "C");
    private static final int TOTAL_SIZE = 3 * 2 * 2;

    @Test
    public void buildEmptyDoesNotThrow() {
        PositionIndexer mapping = PositionIndexer.builder().build();
        assertNotNull(mapping);
    }

    @Test
    public void threeDimensionPositionsAreUnique() {
        Builder builder = PositionIndexer.builder();
        builder.put(String.class, STRINGS);
        builder.put(Integer.class, INTS);
        builder.put(Long.class, LONGS);
        PositionIndexer mapping = builder.build();

        ImmutableSet.Builder<Integer> builder1 = ImmutableSet.builder();
        for (Position position : allPositions()) {
            builder1.add(mapping.indexFor(position));
        }
        ImmutableSet<Integer> indizes = builder1.build();

        assertThat(indizes.size(), equalTo(TOTAL_SIZE));
        assertThat(indizes, equalTo(ImmutableSet.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)));
    }

    private Set<Position> allPositions() {
        ImmutableSet.Builder<Position> builder = ImmutableSet.builder();
        for (String string : STRINGS) {
            for (Integer intValue : INTS) {
                for (Long longValue : LONGS) {
                    builder.add(Position.of(string, intValue, longValue));
                }
            }
        }
        return builder.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void putTwiceSameDimensionThrows() {
        Builder builder = PositionIndexer.builder();
        builder.put(String.class, STRINGS);
        builder.put(String.class, STRINGS);
    }

}
