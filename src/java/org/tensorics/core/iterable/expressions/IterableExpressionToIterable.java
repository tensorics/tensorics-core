/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.expressions;

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
public class IterableExpressionToIterable<T> extends AbstractDeferredExpression<Iterable<T>> {

    private final List<? extends Expression<T>> expressions;

    public IterableExpressionToIterable(Iterable<? extends Expression<T>> expressions) {
        super();
        this.expressions = Lists.newArrayList(expressions);
    }

    @Override
    public List<? extends Expression<T>> getChildren() {
        return this.expressions;
    }

}
