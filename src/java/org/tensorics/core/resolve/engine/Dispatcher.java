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