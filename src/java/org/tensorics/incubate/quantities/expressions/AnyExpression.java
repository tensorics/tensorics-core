/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.expressions;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.incubate.quantities.Any;

import com.google.common.collect.ImmutableList;

public class AnyExpression<R> extends AbstractDeferredExpression<R> {
    private static final long serialVersionUID = 1L;

    private final Expression<Any> any;

    public AnyExpression(Expression<Any> source) {
        this.any = requireNonNull(source);
    }

    @Override
    public List<? extends Node> getChildren() {
        return ImmutableList.of(any());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((any() == null) ? 0 : any().hashCode());
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
        AnyExpression<?> other = (AnyExpression<?>) obj;
        if (any() == null) {
            if (other.any() != null) {
                return false;
            }
        } else if (!any().equals(other.any())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AnyExpression [any=" + any() + "]";
    }

    public Expression<Any> any() {
        return any;
    }

}
