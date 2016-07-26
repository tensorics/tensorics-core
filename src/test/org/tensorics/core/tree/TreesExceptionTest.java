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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.tree.domain.ExceptionHandlingNode;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.Path;
import org.tensorics.core.tree.walking.PathDoesNotExistException;
import org.tensorics.core.tree.walking.StepUpCallback;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

public class TreesExceptionTest {

    private Node rootNode;

    private Node child1;
    private Node child2;

    private Node child11;
    private Node child21;
    private Node childCommon;

    private static interface ExceptionHandlingNodeWithChildren<R> extends ExceptionHandlingNode<R>, Node {
        /* Nothing to do, but required for mocking */
    }

    // @formatter:off
	/*
	 * 
	 * root / \ / \ child1(EH) child2(EH) / \ / \ / \/ \child11 cc child21(EH)
	 */
	// @formatter:on

    @Before
    public void setUp() throws Exception {
        rootNode = mock(Node.class);
        child1 = mock(ExceptionHandlingNodeWithChildren.class);
        child2 = mock(ExceptionHandlingNodeWithChildren.class);
        child11 = mock(Node.class);
        child21 = mock(ExceptionHandlingNode.class);
        childCommon = mock(Node.class);

        when(rootNode.getChildren()).thenAnswer((args)-> ImmutableList.<Node> of(child1, child2));
        when(child1.getChildren()).thenAnswer((args)-> ImmutableList.<Node> of(child11, childCommon));
        when(child2.getChildren()).thenAnswer((args)-> ImmutableList.<Node> of(child21, childCommon));

        when(rootNode.toString()).thenReturn("rootNode");
        when(child1.toString()).thenReturn("child1");
        when(child2.toString()).thenReturn("child2");
        when(child11.toString()).thenReturn("child11");
        when(child21.toString()).thenReturn("child21");
        when(childCommon.toString()).thenReturn("childCommon");
    }

    @Test
    public void testFindBottomNodes() {
        Collection<Node> bottomNodes = Trees.findBottomNodes(rootNode);
        List<Node> expectedNodes = ImmutableList.of(child11, childCommon, child21);
        assertEquals(expectedNodes.size(), bottomNodes.size());
        for (Node expectedNode : expectedNodes) {
            assertTrue(bottomNodes.contains(expectedNode));
        }
    }

    @Test
    public void testSubTreeContentFullTree() {
        List<Node> allNodes = Trees.subTreeContent(rootNode);
        assertEquals(ImmutableList.of(child11, childCommon, child1, child21, childCommon, child2, rootNode), allNodes);
    }

    @Test
    public void testSubTreeContentOneNode() {
        List<Node> content = Trees.subTreeContent(childCommon);
        assertEquals(ImmutableList.of(childCommon), content);
    }

    @Test(expected = PathDoesNotExistException.class)
    public void testPathRootTo22() {
        assertNotNull(rootNode);
        assertNotNull(child21);
        Trees.getPathsFromChildToAncestor(rootNode, child21);
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
    public void testPathFromNodeToParentContains2Elements() {
        List<Path> content = Trees.getPathsFromChildToAncestor(child21, child2);
        assertEquals(ImmutableList.of(child21, child2), content.get(0).getPath());
    }

    @Test
    public void testPathFromChild21ToRootContains3Elements() {
        List<Path> content = Trees.getPathsFromChildToAncestor(child21, rootNode);
        assertEquals(ImmutableList.of(child21, child2, rootNode), content.get(0).getPath());
    }

    @Test
    public void testPathFromChild11ToRootContains3Elements() {
        List<Path> content2 = Trees.getPathsFromChildToAncestor(child11, rootNode);
        assertEquals(ImmutableList.of(child11, child1, rootNode), content2.get(0).getPath());
    }

    @Test
    public void testFindPathFromCommonChildToRoot() throws Exception {
        List<Path> content = Trees.getPathsFromChildToAncestor(childCommon, rootNode);
        assertEquals(ImmutableList.of(childCommon, child1, rootNode), content.get(0).getPath());
    }

    @Test
    public void test11ToRootSize() throws Exception {
        assertEquals(1, paths11ToRoot().size());
    }

    @Test
    public void test11ToRootDetails() throws Exception {
        List<Node> path = Trees.getPathsFromChildToAncestor(child11, rootNode).get(0).getPath();
        assertEquals(3, path.size());
        assertEquals(child11, path.get(0));
        assertEquals(rootNode, path.get(2));
    }

    private List<Path> paths11ToRoot() {
        return Trees.getPathsFromChildToAncestor(child11, rootNode);
    }

    private List<Path> pathsFrom2To2() {
        return Trees.getPathsFromChildToAncestor(child2, child2);
    }

    @Test
    public void testFindClosesHandlingNode11() {
        assertFoundNodes(child11, child1);
    }

    @Test
    public void testFindClosestHandlingNode21() {
        assertFoundNodes(child21, child21);
    }

    @Test
    public void testFindClosestHandlingNodeCommon() {
        assertFoundNodes(childCommon, child1, child2);
    }

    @Test
    public void testPathFromNodeToItself() {
        assertEquals(1, sizeOfPath(child11, child11));
        assertEquals(1, sizeOfPath(child1, child1));
        assertEquals(1, sizeOfPath(rootNode, rootNode));
    }

    @Test
    public void testPathForPathWith2Nodes() {
        assertEquals(2, sizeOfPath(child11, child1));
        assertEquals(2, sizeOfPath(child1, rootNode));
        assertEquals(2, sizeOfPath(childCommon, child2));
        assertEquals(2, sizeOfPath(childCommon, child1));
        assertEquals(2, sizeOfPath(child2, rootNode));
        assertEquals(2, sizeOfPath(child21, child2));
    }

    @Test
    public void getPathsFromCommonToRoot() {
        List<Path> paths = Trees.getPathsFromChildToAncestor(childCommon, rootNode);
        assertEquals(2, paths.size());
        assertEquals(ImmutableList.of(childCommon, child1, rootNode), paths.get(0).getPath());
        assertEquals(ImmutableList.of(childCommon, child2, rootNode), paths.get(1).getPath());
    }

    @Test
    public void getPathsFromChild11NodeToRoot() {
        List<Path> paths1 = Trees.getPathsFromChildToAncestor(child11, rootNode);
        assertEquals(1, paths1.size());
        assertEquals(ImmutableList.of(child11, child1, rootNode), paths1.get(0).getPath());
    }

    @Test
    public void getPathsFromChild21NodeToRoot() {
        List<Path> paths2 = Trees.getPathsFromChildToAncestor(child21, rootNode);
        assertEquals(1, paths2.size());
        assertEquals(ImmutableList.of(child21, child2, rootNode), paths2.get(0).getPath());
    }

    @Test
    public void testCollectPaths() { // NOSONAR no assert required
        final AtomicBoolean wasCalled = new AtomicBoolean(false);
        Trees.walkParentAfterChildren(rootNode, new StepUpCallback() {

            @Override
            public void onStepUpFromChildToParent(Node children, Node parent) {
                wasCalled.set(true);
            }
        });
        assertTrue(wasCalled.get());
    }

    private int sizeOfPath(Node node1, Node node2) {
        return Trees.getPathsFromChildToAncestor(node1, node2).get(0).getPath().size();
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
