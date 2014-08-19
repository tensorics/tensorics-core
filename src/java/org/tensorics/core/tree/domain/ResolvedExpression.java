package org.tensorics.core.tree.domain;

import java.util.Collections;
import java.util.List;

/**
 * An expression that needs no further processing. It contains already its own result, which can be simply retrieved by
 * the {@link #get()} method.
 * 
 * @author kfuchsbe
 * @param <R> the type of the resulting value of the expression
 */
public final class ResolvedExpression<R> implements Expression<R> {

    private final R value;

    private ResolvedExpression(R value) {
        this.value = value;
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static <T> ResolvedExpression<T> of(T value) {
        return new ResolvedExpression<T>(value);
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public R get() {
        return value;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

}