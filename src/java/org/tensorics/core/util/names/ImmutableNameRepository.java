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

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Immutable repository for object's name backed by a {@link Map}. Resolves the name of an object using the {@link Map}
 * specified in the constructor. In case no name can be found within the map, then {@code null is returned}.
 *
 * @see #nameFor(Object)
 */
public class ImmutableNameRepository implements NameRepository {

    private final Map<Object, String> objectNames;

    /**
     * Creates a new name repository backed by the given map.
     *
     * @param objectNames the mapping from objects to names
     */
    private ImmutableNameRepository(Map<Object, String> objectNames) {
        requireNonNull(objectNames, "objectNames must not be null");
        this.objectNames = ImmutableMap.copyOf(objectNames);
    }

    public static ImmutableNameRepository empty() {
        return new ImmutableNameRepository(Collections.emptyMap());
    }

    public static ImmutableNameRepository fromMap(Map<Object, String> objectNames) {
        return new ImmutableNameRepository(objectNames);
    }

    @Override
    public String nameFor(Object object) {
        return objectNames.get(object);
    }

    public Map<Object, String> content() {
        return this.objectNames;
    }

}
