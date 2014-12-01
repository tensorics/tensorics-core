/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.impl;

import org.tensorics.core.math.Cheating;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * allows to hook in different implementations for more complicated mathematical operations.
 * 
 * @author kfuchsbe
 * @param <T> the type of elements of the field
 */
public class ExtendedFieldImpl<T> extends ExplicitFieldImpl<T> implements ExtendedField<T> {

    private final org.tensorics.core.math.Math<T> math;
    private final Cheating<T> cheatingInstance;
    private final BinaryOperation<T> powerOperation = new BinaryOperation<T>() {
        @Override
        public T perform(T left, T right) {
            return math.pow(left, right);
        }
    };

    public ExtendedFieldImpl(OrderedField<T> field, org.tensorics.core.math.Math<T> math, Cheating<T> cheating) {
        super(field);
        this.math = math;
        this.cheatingInstance = cheating;
    }

    @Override
    public BinaryOperation<T> power() {
        return powerOperation;
    }

    /**
     * Returns the instance which provides methods to convert field elements from and to double values. It is intended
     * that this mechanism can be replaced later by a re implementation of the unit-calculations which are fully based
     * on field elements. To avoid heavy usage until then, this method is marked as deprecated.
     * 
     * @return an object providing conversion methods from/to double values
     * @deprecated because it shall be removed as soon as a better way to handle units (based on field elements) is
     *             provided
     */
    @Override
    @Deprecated
    public Cheating<T> cheating() {
        return cheatingInstance;
    }

}
