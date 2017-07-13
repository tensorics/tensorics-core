/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.expressions;

import static org.tensorics.core.iterable.expressions.PickExpression.Mode.FROM_END;
import static org.tensorics.core.iterable.expressions.PickExpression.Mode.FROM_START;

import java.io.Serializable;
import java.util.List;

import org.tensorics.core.resolve.resolvers.PickResolver;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Deferred expression that can pick an element of an, {@link Expression} of, {@link Iterable}. The {@link Mode} and the
 * offset define the resulting element.
 *
 * @see PickResolver
 * @author kfuchsbe, acalia
 * @param <T> the type of the data to pick
 */
public class PickExpression<T> extends AbstractDeferredExpression<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Expression<? extends Iterable<T>> iterable;
    private final int offset;
    private final Mode mode;

    private PickExpression(Expression<? extends Iterable<T>> iterable, int offset, Mode mode) {
        super();
        this.iterable = iterable;
        this.offset = offset;
        this.mode = mode;
    }

    public static <T> PickExpression<T> fromFirst(Expression<? extends Iterable<T>> iterable, int offset) {
        return new PickExpression<>(iterable, offset, FROM_START);
    }

    public static <T> PickExpression<T> fromLast(Expression<? extends Iterable<T>> iterable, int offset) {
        return new PickExpression<>(iterable, offset, FROM_END);
    }

    @Override
    public List<? extends Node> getChildren() {
        return ImmutableList.of(iterable);
    }

    public Expression<? extends Iterable<T>> iterableExpression() {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((iterable == null) ? 0 : iterable.hashCode());
        result = prime * result + ((mode == null) ? 0 : mode.hashCode());
        result = prime * result + offset;
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
        PickExpression<?> other = (PickExpression<?>) obj;
        if (iterable == null) {
            if (other.iterable != null) {
                return false;
            }
        } else if (!iterable.equals(other.iterable)) {
            return false;
        }
        if (mode != other.mode) {
            return false;
        }
        if (offset != other.offset) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PickExpression [iterable=" + iterable + ", offset=" + offset + ", mode=" + mode + "]";
    }

}
