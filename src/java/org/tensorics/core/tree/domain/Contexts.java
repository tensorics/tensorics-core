/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;

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
