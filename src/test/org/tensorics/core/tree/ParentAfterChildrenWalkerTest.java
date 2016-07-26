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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.walking.BottomNodeCallback;
import org.tensorics.core.tree.walking.EveryNodeCallback;
import org.tensorics.core.tree.walking.ParentAfterChildrenWalker;
import org.tensorics.core.tree.walking.SkipNodeAndSubTreesCallback;
import org.tensorics.core.tree.walking.StepUpCallback;

import com.google.common.collect.ImmutableList;

public class ParentAfterChildrenWalkerTest {

    private ParentAfterChildrenWalker walker;
    private Node rootNode;

    private Node child1;
    private Node child2;

    private EveryNodeCallback callback;
    private EveryNodeAndSkipCallback skipCallback;
    private BottomNodeCallback bottomNodeCallback;
    private StepUpCallback stepUpCallback;

    private Node child11;
    private Node child12;
    private Node child21;
    private Node child221;
    private Node child22;

    @Before
    public void setUp() throws Exception {
        this.walker = new ParentAfterChildrenWalker();

        rootNode = mock(Node.class);

        child1 = mock(Node.class);
        child2 = mock(Node.class);
        child11 = mock(Node.class);
        child12 = mock(Node.class);
        child21 = mock(Node.class);
        child22 = mock(Node.class);
        child221 = mock(Node.class);

        callback = mock(EveryNodeCallback.class);
        skipCallback = mock(EveryNodeAndSkipCallback.class);
        bottomNodeCallback = mock(BottomNodeCallback.class);
        stepUpCallback = mock(StepUpCallback.class);
    }

    private static interface EveryNodeAndSkipCallback extends SkipNodeAndSubTreesCallback, EveryNodeCallback {
        /* has to do nothing on purpose */
    }

    @Test(expected = NullPointerException.class)
    public void testNullNode() {
        assertNotNull(callback);
        walker.walk(null, callback);
    }

    @Test(expected = NullPointerException.class)
    public void testNullCallback() {
        assertNotNull(rootNode);
        walker.walk(rootNode, null);
    }

    @Test
    public void testOneNode() {
        walker.walk(rootNode, callback);
        verify(callback, times(1)).onEvery(rootNode);
        assertNoMoreInteractions(callback);
    }

    private void assertNoMoreInteractions(EveryNodeCallback mock) {
        verifyNoMoreInteractions(mock);
    }

    @Test(expected = NullPointerException.class)
    public void testOneNodeMockedNullChildren() {
        assertNotNull(rootNode);
        when(rootNode.getChildren()).thenReturn(null);
        walker.walk(rootNode, callback);
    }

    @Test
    public void testFirstLevelVisited() {
        setUpRootNode();
        walker.walk(rootNode, callback);
        assertCallbackInOrder(callback, child1, child2, rootNode);
    }

    @Test
    public void testAllVisited() {
        setUpAll();
        walker.walk(rootNode, callback);
        assertCallbackInOrder(callback, child11, child12, child1, child21, child221, child22, child2, rootNode);
    }

    @Test
    public void testSkipChild22SubTree() {
        setUpAll();
        when(skipCallback.shallBeSkipped(child22)).thenReturn(true);
        walker.walk(rootNode, skipCallback);
        verify(skipCallback, times(7)).shallBeSkipped(any(Node.class));
        assertCallbackInOrder(skipCallback, child11, child12, child1, child21, child2, rootNode);
    }

    @Test
    public void testBottomNodeCallback() {
        setUpAll();
        walker.walk(rootNode, bottomNodeCallback);
        assertBottomCallbackInOrder(bottomNodeCallback, child11, child12, child21, child221);
    }

    @Test
    public void testStepUpCallback() {
        setUpAll();
        walker.walk(rootNode, stepUpCallback);
        assertStepUpCallbackInOrder();
    }

    private void assertStepUpCallbackInOrder() {
        InOrder inOrder = inOrder(stepUpCallback);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child11, child1);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child12, child1);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child1, rootNode);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child21, child2);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child221, child22);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child22, child2);
        inOrder.verify(stepUpCallback).onStepUpFromChildToParent(child2, rootNode);
        verifyNoMoreInteractions(stepUpCallback);
    }

    private void assertBottomCallbackInOrder(BottomNodeCallback nodeCallback, Node... nodes) {
        InOrder inOrder = inOrder(nodeCallback);
        for (Node node : nodes) {
            inOrder.verify(nodeCallback).onBottom(node);
        }
        verifyNoMoreInteractions(nodeCallback);
    }

    public void setUpAll() {
        setUpRootNode();
        when(child1.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child11, child12));
        when(child2.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child21, child22));
        when(child22.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child221));
    }

    private void setUpRootNode() {
        when(rootNode.getChildren()).thenAnswer((args) -> ImmutableList.<Node> of(child1, child2));
    }

    private void assertCallbackInOrder(EveryNodeCallback everyNodeCallback, Node... nodes) {
        InOrder inOrder = inOrder(everyNodeCallback);
        for (Node node : nodes) {
            inOrder.verify(everyNodeCallback).onEvery(node);
        }
        assertNoMoreInteractions(everyNodeCallback);
    }
}
