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

package org.tensorics.core.booleans.operations;

import static java.lang.Boolean.TRUE;
import static java.util.stream.StreamSupport.stream;

import java.util.NoSuchElementException;

import org.tensorics.core.analysis.lang.IterableBooleanConversion;

import com.google.common.collect.Iterables;

/**
 * {@link IterableBooleanConversion} that performs a logical OR over all the elements of the iterable.
 * 
 * @author acalia, caguiler, kfuchsberger
 */
public class Or implements IterableBooleanConversion {

    @Override
    public Boolean apply(Iterable<Boolean> booleanList) {
        if (Iterables.isEmpty(booleanList)) {
            throw new NoSuchElementException("Cannot perform a logical OR with no elements");
        }
        return stream(booleanList.spliterator(), false).anyMatch(TRUE::equals);
    }

    @Override
    public String toString() {
        return "OR";
    }

}
