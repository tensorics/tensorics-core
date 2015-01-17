/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.scalar.lang;

import org.tensorics.core.commons.lang.BasicOperationSupport;
import org.tensorics.core.commons.lang.OngoingBinaryOperation;
import org.tensorics.core.math.ExtendedField;

/**
 * Contains the starting methods for tensoric eDSL language expressions, related to basic operations of scalar values
 * (field elements). The class is designed that it can be used in both ways: Either that it can be instantiated or that
 * other classes can inherit from it. The latter option makes the language expressions very concise.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values (elements of the fields) on which to operate
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ScalarSupport<V> implements BasicOperationSupport<V> {

    private final ExtendedField<V> field;

    public ScalarSupport(ExtendedField<V> field) {
        this.field = field;
    }

    @Override
    public OngoingBinaryOperation<V> calculate(V operand) {
        return new OngoingScalarBinaryOperation<V>(this.field, operand);
    }

    public OngoingScalarBinaryPredicate<V> testIf(V left) {
        return new OngoingScalarBinaryPredicate<V>(field, left);
    }

    @Override
    public final V negativeOf(V element) {
        return this.field.additiveInversion().perform(element);
    }

    @Override
    public final V inverseOf(V element) {
        return this.field.multiplicativeInversion().perform(element);
    }

    public final V zero() {
        return this.field.zero();
    }

    public final V two() {
        return calculate(one()).plus(one());
    }

    public final V one() {
        return this.field.one();
    }

    public V countOf(int number) {
        V one = one();
        V count = zero();
        for (int i = 0; i < number; i++) {
            count = calculate(count).plus(one);
        }
        return count;
    }

    @Override
    public V squareRootOf(V value) {
        return calculate(value).root(two());
    }

    @Override
    public V squareOf(V value) {
        return calculate(value).toThePowerOf(two());
    }

    @Override
    public V absoluteValueOf(V value) {
        return squareRootOf(squareOf(value));
    }

}
