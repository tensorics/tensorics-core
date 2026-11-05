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
package org.tensorics.core.tensor;

import static org.tensorics.core.tensor.Positions.assertConsistentDimensions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.google.common.collect.ImmutableSet;

public class PositionTest {

    private static final Position POS_A = Position.of("A");

    @Test
    public void coordinatesContainPositionThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Position.of(POS_A));
        assertThat(exception.getMessage(), containsString("A position is contained"));
    }

    @Test
    public void assertConsistentWithCorrectSet() {
        assertConsistentDimensions(POS_A, ImmutableSet.of(String.class));
    }

    @Test
    public void assertConsistentWithWrongSetThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> assertConsistentDimensions(POS_A, ImmutableSet.of(Integer.class)));
        assertThat(exception.getMessage(), containsString("assignable"));
    }

    @Test
    public void positionOfSameTypeAndNonEqualThrow() {
        assertUniqueDimensionsException(() -> Position.of("A", "B"));
    }

    @Test
    public void positionOfSetSameTypeThrow() {
        assertUniqueDimensionsException(() -> Position.of(ImmutableSet.of("A", "B")));
    }

    @Test
    public void positionOfSameTypeAndSameValueThrow() {
        assertUniqueDimensionsException(() -> Position.of("A", "A"));
    }

    private static void assertUniqueDimensionsException(Executable executable) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertThat(exception.getMessage(), containsString("unique dimensions"));
    }

}
