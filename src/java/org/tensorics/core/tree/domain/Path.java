/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tree.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Path containing all {@link Node}s from the exception node to the root
 * 
 * @author pturcu
 */
public final class Path {

    private final List<Node> nodes = new ArrayList<>();

    public Path() {
        this(Collections.<Node> emptyList());
    }

    public Path(List<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    public List<Node> getPath() {
        return new ArrayList<Node>(nodes);
    }

    public void add(Node node) {
        this.nodes.add(node);
    }
}
