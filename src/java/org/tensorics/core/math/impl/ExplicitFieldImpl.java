/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.impl;

import org.tensorics.core.math.BinaryPredicates;
import org.tensorics.core.math.ExplicitField;
import org.tensorics.core.math.Operations;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.predicates.BinaryPredicate;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * @author kfuchsbe
 * @param <T> the type of the field elements
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ExplicitFieldImpl<T> implements ExplicitField<T> {

    private final OrderedField<T> field;
    private final BinaryOperation<T> subtractionOperation;
    private final BinaryOperation<T> divisionOperation;
    private final T twoValue;
    private final BinaryPredicate<T> greaterPredicate;
    private final BinaryPredicate<T> greaterOrEqualPredicate;
    private final BinaryPredicate<T> lessPredicate;
    private final BinaryPredicate<T> equalPredicate;

    public ExplicitFieldImpl(OrderedField<T> field) {
        this.field = field;
        subtractionOperation = Operations.inverseBinaryFor(field.additionStructure());
        divisionOperation = Operations.inverseBinaryFor(field.multiplicationStructure());
        twoValue = addition().perform(one(), one());

        greaterPredicate = BinaryPredicates.not(field.lessOrEqualPredicate());
        greaterOrEqualPredicate = BinaryPredicates.invert(field.lessOrEqualPredicate());
        lessPredicate = BinaryPredicates.not(greaterOrEqualPredicate);
        equalPredicate = BinaryPredicates.and(field.lessOrEqualPredicate(), greaterOrEqualPredicate);
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

    @Override
    public BinaryPredicate<T> less() {
        return lessPredicate;
    }

    @Override
    public BinaryPredicate<T> lessOrEqual() {
        return field.lessOrEqualPredicate();
    }

    @Override
    public BinaryPredicate<T> equal() {
        return equalPredicate;
    }

    @Override
    public BinaryPredicate<T> greaterOrEqual() {
        return greaterOrEqualPredicate;
    }

    @Override
    public BinaryPredicate<T> greater() {
        return greaterPredicate;
    }

}