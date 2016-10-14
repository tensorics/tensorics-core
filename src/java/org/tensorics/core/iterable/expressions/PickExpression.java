/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.expressions;

import static org.tensorics.core.iterable.expressions.PickExpression.Mode.FROM_END;
import static org.tensorics.core.iterable.expressions.PickExpression.Mode.FROM_START;

import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class PickExpression<T> extends AbstractDeferredExpression<T> {

    private final Expression<Iterable<T>> iterable;
    private final int offset;
    private final Mode mode;

    private PickExpression(Expression<Iterable<T>> iterable, int offset, Mode mode) {
        super();
        this.iterable = iterable;
        this.offset = offset;
        this.mode = mode;
    }

    public static <T> PickExpression<T> fromFirst(Expression<Iterable<T>> iterable, int offset) {
        return new PickExpression<T>(iterable, offset, FROM_START);
    }

    public static <T> PickExpression<T> fromLast(Expression<Iterable<T>> iterable, int offset) {
        return new PickExpression<T>(iterable, offset, FROM_END);
    }

    @Override
    public List<? extends Node> getChildren() {
        return ImmutableList.of(iterable);
    }

    public Expression<Iterable<T>> iterable() {
        return iterable;
    }

    public int offset() {
        return offset;
    }

    public Mode mode() {
        return mode;
    }

    public static enum Mode {
        FROM_START {
            @Override
            public <T> T pick(Iterable<T> iterable, int offset) {
                return Iterables.get(iterable, offset);
            }
        },
        FROM_END {
            @Override
            public <T> T pick(Iterable<T> iterable, int offset) {
                int size = Iterables.size(iterable);
                int index = size - offset - 1;
                return Iterables.get(iterable, index);
            }
        };

        public abstract <T> T pick(Iterable<T> iterable, int offset);
    }

}
