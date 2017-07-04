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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.Path;
import org.tensorics.core.tree.walking.PathDoesNotExistException;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.collect.ImmutableList;

public class TreesTest {

    private Node rootNode;

    private Node child1;
    private Node child2;

    private Node child11;
    private Node child12;
    private Node child21;
    private Node child221;
    private Node child22;

    @Before
    public void setUp() throws Exception {
        rootNode = mock(Node.class);

        child1 = mock(Node.class);
        child2 = mock(Node.class);
        child11 = mock(Node.class);
        child12 = mock(Node.class);
        child21 = mock(Node.class);
        child22 = mock(Node.class);
        child221 = mock(Node.class);

        when(rootNode.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child1, child2));
        when(child1.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child11, child12));
        when(child2.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child21, child22));
        when(child22.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child221));
    }

    @Test
    public void testFindBottomNodes() {
        Collection<Node> bottomNodes = Trees.findBottomNodes(rootNode);
        List<Node> expectedNodes = ImmutableList.of(child11, child12, child21, child221);
        assertEquals(expectedNodes.size(), bottomNodes.size());
        for (Node expectedNode : expectedNodes) {
            assertTrue(bottomNodes.contains(expectedNode));
        }
    }

    @Test
    public void testSubTreeContentFullTree() {
        List<Node> allNodes = Trees.subTreeContent(rootNode);
        assertEquals(ImmutableList.of(child11, child12, child1, child21, child221, child22, child2, rootNode),
                allNodes);
    }

    @Test
    public void testSubTreeContentOneNode() {
        List<Node> content = Trees.subTreeContent(child11);
        assertEquals(ImmutableList.of(child11), content);
    }

    @Test
    public void testPathFrom21ToRoot() {
        assertEquals(ImmutableList.of(child21, child2, rootNode).size(),
                Trees.getPathsFromChildToAncestor(child21, rootNode).get(0).getPath().size());
    }

    @Test
    public void testPathFrom22ToRoot() {
        assertEquals(ImmutableList.of(child22, child2, rootNode).size(),
                Trees.getPathsFromChildToAncestor(child22, rootNode).get(0).getPath().size());
    }

    @Test(expected = PathDoesNotExistException.class)
    public void testPathRootTo22() {
        Trees.getPathsFromChildToAncestor(rootNode, child22);
    }

    @Test
    public void testPathForNodeToItself() {
        assertEquals(1, pathsFrom2To2().size());
    }

    @Test
    public void testPathFromNodeToItselfContainsOneElement() {
        assertEquals(child2, pathsFrom2To2().get(0).getPath().get(0));
    }

    @Test
    public void testFindPathFrom221ToRoot() throws Exception {
        assertEquals(1, Trees.getPathsFromChildToAncestor(child221, rootNode).size());
    }

    @Test
    public void testFindPathFrom221ToRootHas3Elements() {
        assertEquals(4, Trees.getPathsFromChildToAncestor(child221, rootNode).get(0).getPath().size());
    }

    private List<Path> pathsFrom2To2() {
        return Trees.getPathsFromChildToAncestor(child2, child2);
    }

}
