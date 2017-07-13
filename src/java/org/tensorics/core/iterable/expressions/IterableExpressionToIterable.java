/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.expressions;

import java.io.Serializable;
import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.Lists;

/**
 * A simple expression which contains a list of expressions of T, which can be resolved into an list of T, by resolving
 * each one individually.
 *
 * @author kfuchsbe
 * @param <T> the type of the resolved elements
 */
public class IterableExpressionToIterable<T> extends AbstractDeferredExpression<Iterable<T>> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<? extends Expression<T>> expressions;

    public IterableExpressionToIterable(Iterable<? extends Expression<T>> expressions) {
        super();
        this.expressions = Lists.newArrayList(expressions);
    }

    @Override
    public List<? extends Expression<T>> getChildren() {
        return this.expressions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expressions == null) ? 0 : expressions.hashCode());
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
        IterableExpressionToIterable<?> other = (IterableExpressionToIterable<?>) obj;
        if (expressions == null) {
            if (other.expressions != null) {
                return false;
            }
        } else if (!expressions.equals(other.expressions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IterableExpressionToIterable [expressions=" + expressions + "]";
    }

}
