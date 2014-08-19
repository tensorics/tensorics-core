/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.impl;

import org.tensorics.core.math.ExplicitField;
import org.tensorics.core.math.Operations;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.structures.ringlike.Field;

/**
 * @author kfuchsbe
 * @param <T>
 */
public class ExplicitFieldImpl<T> implements ExplicitField<T> {

    private final Field<T> field;
    private final BinaryOperation<T> subtractionOperation;
    private final BinaryOperation<T> divisionOperation;
    private final T twoValue;

    public ExplicitFieldImpl(Field<T> field) {
        this.field = field;
        subtractionOperation = Operations.inverseBinaryFor(field.additionStructure());
        divisionOperation = Operations.inverseBinaryFor(field.multiplicationStructure());
        twoValue = addition().perform(one(), one());
    }

    @Override
    public final BinaryOperation<T> addition() {
        return field.additionStructure().operation();
    }

    @Override
    public final BinaryOperation<T> multiplication() {
        return field.multiplicationStructure().operation();
    }

    @Override
    public final UnaryOperation<T> additiveInversion() {
        return field.additionStructure().inversion();
    }

    @Override
    public final BinaryOperation<T> subtraction() {
        return subtractionOperation;
    }

    @Override
    public final T zero() {
        return field.additionStructure().identity();
    }

    @Override
    public final BinaryOperation<T> division() {
        return divisionOperation;
    }

    @Override
    public final UnaryOperation<T> multiplicativeInversion() {
        return field.multiplicationStructure().inversion();
    }

    @Override
    public final T one() {
        return field.multiplicationStructure().identity();
    }

    @Override
    public final T two() {
        return twoValue;
    }

}