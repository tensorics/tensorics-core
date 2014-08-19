/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.operations;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * A repository for operations on quantified values. This way, one instance of each can be re-used all the times.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the field)
 */
@SuppressWarnings("PMD.TooManyMethods")
public class QuantityOperationRepository<S> {

    private final QuantityAddition<S> quantityAddition;
    private final QuantitySubtraction<S> quantitySubtraction;
    private final QuantityMultiplication<S> quantityMultiplication;
    private final QuantityDivision<S> quantityDivision;
    private final QuantityAdditiveInversion<S> quantityAdditiveInversion;
    private final QuantityMultiplicativeInversion<S> quantityMultiplicativeInversion;

    private final QuantifiedValue<S> zeroOfUnitOne;
    private final QuantifiedValue<S> oneOfUnitOne;
    private final QuantifiedValue<S> twoOfUnitOne;
    private final Environment<S> mathsEnvironment;

    public QuantityOperationRepository(QuantityEnvironment<S> environment) {
        this.mathsEnvironment = environment;
        this.quantityAddition = new QuantityAddition<>(environment);
        this.quantitySubtraction = new QuantitySubtraction<>(environment);
        this.quantityAdditiveInversion = new QuantityAdditiveInversion<>(environment);
        this.quantityMultiplication = new QuantityMultiplication<>(environment);
        this.quantityDivision = new QuantityDivision<>(environment);
        this.quantityMultiplicativeInversion = new QuantityMultiplicativeInversion<>(environment);
        this.zeroOfUnitOne = createDimensionless(environment, environment.field().zero());
        this.oneOfUnitOne = createDimensionless(environment, environment.field().one());
        this.twoOfUnitOne = quantityAddition.perform(oneOfUnitOne, oneOfUnitOne);
    }

    private QuantifiedValue<S> createDimensionless(QuantityEnvironment<S> newEnvironment, S value) {
        return ImmutableQuantifiedValue.of(value, newEnvironment.quantification().one());
    }

    public BinaryOperation<QuantifiedValue<S>> addition() {
        return quantityAddition;
    }

    public UnaryOperation<QuantifiedValue<S>> additiveInversion() {
        return this.quantityAdditiveInversion;
    }

    public BinaryOperation<QuantifiedValue<S>> subtraction() {
        return quantitySubtraction;
    }

    public QuantifiedValue<S> zero() {
        return zeroOfUnitOne;
    }

    public BinaryOperation<QuantifiedValue<S>> multiplication() {
        return quantityMultiplication;
    }

    public BinaryOperation<QuantifiedValue<S>> division() {
        return quantityDivision;
    }

    public UnaryOperation<QuantifiedValue<S>> multiplicativeInversion() {
        return this.quantityMultiplicativeInversion;
    }

    public QuantifiedValue<S> one() {
        return oneOfUnitOne;
    }

    public QuantifiedValue<S> two() {
        return twoOfUnitOne;
    }

    public Environment<S> environment() {
        return mathsEnvironment;
    }
}
