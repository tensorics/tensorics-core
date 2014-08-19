/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.scalar.lang;

import org.tensorics.core.commons.lang.OngoingBinaryOperation;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.operations.BinaryOperation;

/**
 * Part of the tensorics fluent API that provides methods to describe the right hand side of binary operations on plain
 * scalars (field elements).
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (field elements)
 */
@SuppressWarnings("PMD.TooManyMethods")
public class OngoingScalarBinaryOperation<S> implements OngoingBinaryOperation<S> {

    private final ExtendedField<S> field;
    private final S left;

    public OngoingScalarBinaryOperation(ExtendedField<S> field, S left) {
        this.field = field;
        this.left = left;
    }

    @Override
    public S plus(S right) {
        return addition().perform(left, right);
    }

    @Override
    public S minus(S right) {
        return subtraction().perform(left, right);
    }

    @Override
    public S times(S right) {
        return multiplication().perform(left, right);
    }

    @Override
    public S dividedBy(S right) {
        return division().perform(left, right);
    }

    @Override
    public final S toThePowerOf(S power) {
        return power().perform(left, power);
    }

    @Override
    public final S root(S element) {
        return power().perform(left, field.multiplicativeInversion().perform(element));
    }

    private final BinaryOperation<S> addition() {
        return field.addition();
    }

    private final BinaryOperation<S> subtraction() {
        return field.subtraction();
    }

    private final BinaryOperation<S> multiplication() {
        return field.multiplication();
    }

    private final BinaryOperation<S> division() {
        return field.division();
    }

    private BinaryOperation<S> power() {
        return field.power();
    }

}
