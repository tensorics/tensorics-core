/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.expressions;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.incubate.quantities.Quantity;

public class ScaledQuantityExpression<V, Q extends Quantity<V>> extends AbstractDeferredExpression<Q> {
    private static final long serialVersionUID = 1L;

    private final Expression<V> factor;
    private final Expression<Q> unit;

    public ScaledQuantityExpression(Expression<V> factor, Expression<Q> unit) {
        this.factor = requireNonNull(factor, "factor must not be null");
        this.unit = requireNonNull(unit, "unit must not be null");
    }

    @Override
    public List<? extends Node> getChildren() {
        return Arrays.asList(factor(), unit());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((factor() == null) ? 0 : factor().hashCode());
        result = prime * result + ((unit() == null) ? 0 : unit().hashCode());
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
        ScaledQuantityExpression<?, ?> other = (ScaledQuantityExpression<?, ?>) obj;
        if (factor() == null) {
            if (other.factor() != null) {
                return false;
            }
        } else if (!factor().equals(other.factor())) {
            return false;
        }
        if (unit() == null) {
            if (other.unit() != null) {
                return false;
            }
        } else if (!unit().equals(other.unit())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ScaledQuantityExpression [factor=" + factor() + ", unit=" + unit() + "]";
    }

    public Expression<V> factor() {
        return factor;
    }

    public Expression<Q> unit() {
        return unit;
    }

}
