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

package org.tensorics.core.tree.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Provides utility methods for creating different kind of contexts.
 * 
 * @author kfuchsbe
 */
public final class Contexts {

    private static final RebuildingContext EMPTY_CONTEXT = new EmptyRebuildingContext();

    private Contexts() {
        /* only static methods */
    }

    /**
     * returns an immutable empty context.
     * 
     * @return an immutable version of an empty context.
     */
    public static RebuildingContext emptyRebuildingContext() {
        return EMPTY_CONTEXT;
    }

    /**
     * creates a new resolving-context which can be filled with resolved operations.
     * 
     * @return a new instance of a resolving context.
     */
    public static EditableResolvingContext newResolvingContext() {
        return new ResolvingContextImpl();
    }

    /**
     * Merges the contexts of the iterable in the order they appear. Being a context a map-like object the order of
     * insertion matters.
     * 
     * @param contexts the given contexts to merge
     * @return the context with all the values of the given contexts
     */
    public static EditableResolvingContext mergeContextsOrdered(Iterable<ResolvingContext> contexts) {
        EditableResolvingContext newCtx = newResolvingContext();
        contexts.forEach(newCtx::putAllNew);
        return newCtx;
    }

    public static EditableResolvingContext mergeContextsOrdered(ResolvingContext... contexts) {
        return mergeContextsOrdered(Arrays.asList(contexts));
    }

    /**
     * creates a new rebuilding-context which allows the rebuilding of model nodes
     * 
     * @return a new instance of a rebuilding context.
     */
    public static RebuildingContext newRebuildingContext() {
        return new RebuildingContextImpl();
    }

    private static final class EmptyRebuildingContext implements RebuildingContext {
        @Override
        public <T extends Node> T getUpdatedOrSame(T node) {
            return node;
        }

        @Override
        public Map<Node, Node> getRebuildingMap() {
            return Collections.emptyMap();
        }

        @Override
        public boolean containsUpdated(Node node) {
            return false;
        }

        @Override
        public <T extends Node> T getUpdated(T node) {
            return null;
        }

        @Override
        public <T extends Node> void put(T oldNode, T newNode) {
            /* nothing to do here */
        }

    }
}
