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

package org.tensorics.core.tree;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tree.domain.ExceptionHandlingNode;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.walking.NoMatchingNodeFoundException;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

public class TreeWithMultiplePathsTest {

    private Node rootNode;

    private Node child1;
    private Node child2;

    private Node child11;
    private Node child12;
    private Node child21;

    private Node childCommon;

    private static interface ExceptionHandlingNodeWithChildren<R> extends ExceptionHandlingNode<R>, Node {
        /* Nothing to do, but required for mocking */
    }

    /*
     * rootNode / \ / \ child1(EH) child2 / \ \ / \ \ child11 child12 child21 \ / / \ / / childCommon
     */
    @Before
    public void setUp() throws Exception {
        rootNode = mock(Node.class);

        child1 = mock(ExceptionHandlingNodeWithChildren.class);
        child2 = mock(Node.class);
        child11 = mock(Node.class);
        child12 = mock(Node.class);
        child21 = mock(Node.class);

        childCommon = mock(Node.class);

        when(rootNode.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child1, child2));
        when(child1.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child11, child12));
        when(child2.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child21));

        when(child11.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(childCommon));
        when(child12.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(childCommon));
        when(child21.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(childCommon));

        when(rootNode.toString()).thenReturn("rootNode");
        when(child1.toString()).thenReturn("child1");
        when(child2.toString()).thenReturn("child2");
        when(child12.toString()).thenReturn("child12");
        when(child11.toString()).thenReturn("child11");
        when(child21.toString()).thenReturn("child21");
        when(childCommon.toString()).thenReturn("childCommon");
    }

    @Test(expected = NoMatchingNodeFoundException.class)
    public void testFindClosestHandlingNodeCommon() throws Exception {
        assertFoundNodes(childCommon, child1, child2);
    }

    private void assertFoundNodes(Node childNode, Node... expectedNodes) {
        Set<ExceptionHandlingNode<?>> nodes = Trees.findClosestAncestorNodeFromNodesToRootOfType(childNode, rootNode,
                new TypeToken<ExceptionHandlingNode<?>>() {
                    private static final long serialVersionUID = 1L;
                });
        assertEquals(Arrays.asList(expectedNodes).size(), nodes.size());
        for (Node node : expectedNodes) {
            assertEquals(true, nodes.contains(node));
        }
    }

}
