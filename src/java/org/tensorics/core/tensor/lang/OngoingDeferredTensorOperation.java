/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import org.tensorics.core.commons.lang.OngoingOperation;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.math.ExplicitField;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.operations.ElementBinaryOperation;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Part of the fluent API for binary (and higher) operations on tensors. It provides methods to define the remaining
 * operands.
 * 
 * @author agorzaws, kfuchbe
 * @param <V>
 */
public class OngoingDeferredTensorOperation<V> implements OngoingOperation<Expression<Tensor<V>>, V> {

    private final ExplicitField<V> field;
    private final Expression<Tensor<V>> left;
    private final OptionRegistry<ManipulationOption> optionRegistry;

    /**
     * Creates a new instance of the this ongoing operation.
     * 
     * @param field the field to use
     * @param optionRegistry the registry containing all the options to use for the following operations
     * @param left the expression to be used as the left operand of the following operations
     */
    public OngoingDeferredTensorOperation(ExplicitField<V> field, OptionRegistry<ManipulationOption> optionRegistry,
            Expression<Tensor<V>> left) {
        this.field = field;
        this.left = left;
        this.optionRegistry = optionRegistry;
    }

    /**
     * Creates an expression that describes the addition of two tensor expressions.
     * 
     * @param right as tensor to add
     * @return result of summing two tensors
     */
    @Override
    public Expression<Tensor<V>> plus(Expression<Tensor<V>> right) {
        return evaluate(right, field.addition());
    }

    @Override
    public Expression<Tensor<V>> minus(Expression<Tensor<V>> right) {
        return evaluate(right, field.subtraction());
    }

    @Override
    public Expression<Tensor<V>> elementDividedByV(V value) {
        Tensor<V> right = Tensorics.zeroDimensionalOf(value);
        return elementDividedBy(ResolvedExpression.of(right));
    }

    @Override
    public Expression<Tensor<V>> elementTimes(Expression<Tensor<V>> right) {
        return evaluate(right, field.multiplication());
    }

    @Override
    public Expression<Tensor<V>> elementTimesV(V value) {
        Tensor<V> right = Tensorics.zeroDimensionalOf(value);
        return elementTimes(ResolvedExpression.of(right));
    }

    @Override
    public Expression<Tensor<V>> elementDividedBy(Expression<Tensor<V>> right) {
        return evaluate(right, field.division());
    }

    // /**
    // * Calls multiplication operation with question for outupt's {@link ShapingStrategy}
    // *
    // * @param right second tensoric to use
    // * @return Possibility to choose {@link ShapingStrategy}s
    // */
    // public Expression<Tensor<V>> elementTimes(Expression<Tensor<V>> right) {
    //
    // }

    // public Expression<Tensor<V>> elementDividedBy(Expression<Tensor<V>> right) {
    //
    // }
    // /**
    // * Calls multiplication for single value operation.
    // *
    // * @param value to be multiplied in first Tensoric
    // * @return multiplied tensoric result.
    // */
    // public Expression<Tensor<V>> elementTimes(V value) {
    //
    // }

    private Expression<Tensor<V>> evaluate(Expression<Tensor<V>> right, BinaryOperation<V> operation) {
        return new BinaryOperationExpression<>(new ElementBinaryOperation<V>(operation, optionRegistry), left, right);
    }

}
