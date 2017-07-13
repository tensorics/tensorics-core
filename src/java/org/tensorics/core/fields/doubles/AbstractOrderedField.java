/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

import java.io.Serializable;

import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.math.structures.grouplike.AbelianGroup;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * @author kfuchsbe
 */
public class AbstractOrderedField<E> implements OrderedField<E>, Serializable {
    private static final long serialVersionUID = 1L;

    private final AbelianGroup<E> additionGroup;
    private final AbelianGroup<E> multiplicationGroup;
    private final BinaryPredicate<E> lessOrEqual;

    public AbstractOrderedField(AbelianGroup<E> additionGroup, AbelianGroup<E> multiplicationGroup,
            BinaryPredicate<E> lessOrEqual) {
        this.additionGroup = additionGroup;
        this.multiplicationGroup = multiplicationGroup;
        this.lessOrEqual = lessOrEqual;
    }

    @Override
    public AbelianGroup<E> additionStructure() {
        return additionGroup;
    }

    @Override
    public AbelianGroup<E> multiplicationStructure() {
        return multiplicationGroup;
    }

    @Override
    public BinaryPredicate<E> lessOrEqualPredicate() {
        return lessOrEqual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((additionGroup == null) ? 0 : additionGroup.hashCode());
        result = prime * result + ((lessOrEqual == null) ? 0 : lessOrEqual.hashCode());
        result = prime * result + ((multiplicationGroup == null) ? 0 : multiplicationGroup.hashCode());
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
        AbstractOrderedField<?> other = (AbstractOrderedField<?>) obj;
        if (additionGroup == null) {
            if (other.additionGroup != null) {
                return false;
            }
        } else if (!additionGroup.equals(other.additionGroup)) {
            return false;
        }
        if (lessOrEqual == null) {
            if (other.lessOrEqual != null) {
                return false;
            }
        } else if (!lessOrEqual.equals(other.lessOrEqual)) {
            return false;
        }
        if (multiplicationGroup == null) {
            if (other.multiplicationGroup != null) {
                return false;
            }
        } else if (!multiplicationGroup.equals(other.multiplicationGroup)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractOrderedField [additionGroup=" + additionGroup + ", multiplicationGroup=" + multiplicationGroup
                + ", lessOrEqual=" + lessOrEqual + "]";
    }

}