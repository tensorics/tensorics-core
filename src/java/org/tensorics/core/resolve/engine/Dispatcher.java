/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine;

import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.resolve.options.ResolvingOption;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * An implementation of this interface is responsible for processing a tree of instruction nodes and resolving as many
 * nodes as possible within one resolver.
 * <p>
 * The actual algorithm to accomplish this task depends on the implementation.
 * 
 * @author kfuchsbe
 */
public interface Dispatcher {

    /**
     * process the tree, starting from the given node and resolve as many nodes as possible.
     * 
     * @param rootNode the node which shall be considered as root node of the tree to process
     * @param actualContext the current state of the context, which can be used by resolvers to look up some values.
     * @param processingOptions
     * @return a context which shall contain the resolved values.
     */
    ResolvingContext processTree(Node rootNode, ResolvingContext actualContext,
            OptionRegistry<ResolvingOption> processingOptions);

}