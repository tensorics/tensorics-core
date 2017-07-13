/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableList;

public class PredicateExpression<T> extends AbstractDeferredExpression<Boolean> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Expression<T> source;
    private final Expression<Predicate<T>> predicate;

    private PredicateExpression(Expression<T> source, Expression<Predicate<T>> predicate) {
        super();
        this.source = source;
        this.predicate = predicate;
    }

    public static <T> PredicateExpression<T> ofSourceAndPredicate(Expression<T> source,
            Expression<Predicate<T>> predicate) {
        return new PredicateExpression<>(source, predicate);
    }

    @Override
    public List<Expression<?>> getChildren() {
        return ImmutableList.of(source, predicate);
    }

    public Expression<T> source() {
        return source;
    }

    public Expression<Predicate<T>> predicate() {
        return predicate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
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
        PredicateExpression<?> other = (PredicateExpression<?>) obj;
        if (predicate == null) {
            if (other.predicate != null) {
                return false;
            }
        } else if (!predicate.equals(other.predicate)) {
            return false;
        }
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PredicateCondition [source=" + source + ", predicate=" + predicate + "]";
    }

}
