/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import java.util.List;

import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.Iterables;

/**
 * Expression that given an {@link Expression} of {@link List} of T, it gets the latest. Especially usefull for buffers.
 * 
 * @param <T>
 */
public class LatestOfExpression<T> extends ConversionOperationExpression<Iterable<T>, T> {

    private static final long serialVersionUID = 6562010523280754793L;

    private final Expression<Iterable<T>> bufferExpression;

    private LatestOfExpression(Expression<Iterable<T>> source) {
        super(Iterables::getLast, source);
        bufferExpression = source;
    }

    public static <T> LatestOfExpression<T> latestOf(Expression<Iterable<T>> source) {
        return new LatestOfExpression<>(source);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((bufferExpression == null) ? 0 : bufferExpression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        LatestOfExpression<?> other = (LatestOfExpression<?>) obj;
        if (bufferExpression == null) {
            if (other.bufferExpression != null)
                return false;
        } else if (!bufferExpression.equals(other.bufferExpression))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LatestOfExpression [bufferExpression=" + bufferExpression + "]";
    }

}
