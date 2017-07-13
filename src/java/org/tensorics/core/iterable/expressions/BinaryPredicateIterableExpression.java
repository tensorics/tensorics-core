// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on
package org.tensorics.core.iterable.expressions;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;

import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.collect.ImmutableList;

/**
 * An unresolved expression which can be resolved by applying a binary predicate on the results of the two operands
 * (left, right). Any instance contains the binary predicate itself as well as expressions for both operands.
 *
 * @param <T> the type of the results of the operands
 * @author caguiler
 */
public class BinaryPredicateIterableExpression<T> extends AbstractDeferredExpression<Boolean> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final BinaryPredicate<T> predicate;
    private final Expression<Iterable<T>> left;
    private final Expression<T> right;

    public BinaryPredicateIterableExpression(BinaryPredicate<T> predicate, Expression<Iterable<T>> left,
            Expression<T> right) {
        super();
        this.predicate = checkNotNull(predicate, "Predicate must not be null!");
        this.left = checkNotNull(left, "Left operand must not be null!");
        this.right = checkNotNull(right, "Right operand must not be null!");
    }

    public BinaryPredicate<T> getPredicate() {
        return predicate;
    }

    public Expression<Iterable<T>> getLeft() {
        return left;
    }

    public Expression<T> getRight() {
        return right;
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.of(left, right);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BinaryPredicateIterableExpression other = (BinaryPredicateIterableExpression) obj;
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }
        if (predicate == null) {
            if (other.predicate != null) {
                return false;
            }
        } else if (!predicate.equals(other.predicate)) {
            return false;
        }
        if (right == null) {
            if (other.right != null) {
                return false;
            }
        } else if (!right.equals(other.right)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BinaryPredicateIterableExpression [predicate=" + predicate + ", left=" + left + ", right=" + right
                + "]";
    }

}
