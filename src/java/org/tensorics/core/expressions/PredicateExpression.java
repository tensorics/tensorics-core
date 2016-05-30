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
package org.tensorics.core.expressions;

import com.google.common.collect.ImmutableList;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PredicateExpression<T> extends AbstractDeferredExpression<T> {

    private final BinaryPredicate<T> predicate;
    private final Expression<T> left;
    private final Expression<T> right;

    public PredicateExpression(BinaryPredicate<T> operation, Expression<T> left, Expression<T> right) {
        super();
        this.predicate = checkNotNull(operation, "Operation must not be null!");
        this.left = checkNotNull(left, "Left operand must not be null!");
        this.right = checkNotNull(right, "Right operand must not be null!");
    }

    public BinaryPredicate<T> getPredicate() {
        return predicate;
    }
 
    public Expression<T> getLeft() {
        return left;
    }

    public Expression<T> getRight() {
        return right;
    }

    @Override
    public List<Node> getChildren() {
        return ImmutableList.<Node> of(left, right);
    }

}
