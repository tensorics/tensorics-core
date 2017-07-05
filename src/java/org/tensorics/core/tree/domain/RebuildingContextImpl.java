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

import java.util.HashMap;
import java.util.Map;

/**
 * {@link RebuildingContextImpl} implements {@link RebuildingContext} storing the relation from an old node to the new
 * node in a {@link Map} in which the key is the old node.
 * 
 * @author agorzaws
 */
public class RebuildingContextImpl implements RebuildingContext {

    private final Map<Node, Node> nodes;

    /**
     * Default constructor
     */
    public RebuildingContextImpl() {
        this.nodes = new HashMap<Node, Node>();
    }

    /**
     * Constructor creating a {@link RebuildingContext} out of an old {@link RebuildingContext}
     * 
     * @param oldContext the old rebuilding context
     */
    public RebuildingContextImpl(RebuildingContext oldContext) {
        this.nodes = new HashMap<>(oldContext.getRebuildingMap());
    }

    @Override
    public <T extends Node> void put(T key, T newNode) {
        nodes.put(key, newNode);
    }

    @Override
    public boolean containsUpdated(Node node) {
        return nodes.containsKey(node);
    }

    @Override
    public <T extends Node> T getUpdatedOrSame(T node) {
        T newNode = getUpdated(node);
        if (newNode == null) {
            return node;
        }
        return newNode;
    }

    @Override
    public Map<Node, Node> getRebuildingMap() {
        return new HashMap<>(this.nodes);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Node> T getUpdated(T node) {
        /*
         * This cast is save, since we only allow to put values of the same type of the key
         */
        return (T) nodes.get(node);
    }
}
