package org.tensorics.core.tree.walking;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

public class TreesFindNodeOfClassTest {

    @Test
    public void findNodeOfExpressionClass() {
        AnyExpression expr1 = new AnyExpression();
        AnyExpression expr2 = new AnyExpression();
        AnyMarkedExpression markedExpr1 = new AnyMarkedExpression();
        AnyMarkedExpression markedExpr2 = new AnyMarkedExpression();

        Expression<?> group = new GroupExpression(Arrays.asList(expr1, expr2, markedExpr1, markedExpr2));
        Set<AnyExpression> result = Trees.findNodesOfClass(group, AnyExpression.class);
        Assertions.assertThat(result).containsOnly(expr1, expr2);
    }

    @Test
    public void findNodeOfMarkerClass() {
        AnyExpression expr1 = new AnyExpression();
        AnyExpression expr2 = new AnyExpression();
        AnyMarkedExpression markedExpr1 = new AnyMarkedExpression();
        AnyMarkedExpression markedExpr2 = new AnyMarkedExpression();

        Expression<?> group = new GroupExpression(Arrays.asList(expr1, expr2, markedExpr1, markedExpr2));
        Set<Marker> result = Trees.findNodesOfClass(group, Marker.class);
        Assertions.assertThat(result).containsOnly(markedExpr1, markedExpr2);
    }

    private interface Marker {
        /* Just a marker interface */
    }

    private class AnyExpression extends AbstractDeferredExpression<Void> {
        @Override
        public List<? extends Node> getChildren() {
            return Collections.emptyList();
        }
    }

    private class AnyMarkedExpression extends AbstractDeferredExpression<Void> implements Marker {
        @Override
        public List<? extends Node> getChildren() {
            return Collections.emptyList();
        }
    }

    private class GroupExpression extends AbstractDeferredExpression<Void> {
        private final List<? extends Node> children;

        public GroupExpression(List<? extends Node> children) {
            this.children = children;
        }

        @Override
        public List<? extends Node> getChildren() {
            return children;
        }
    }

}
