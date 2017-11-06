/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import static java.lang.String.format;

import java.util.List;

import org.tensorics.core.expressions.LatestOfExpression;
import org.tensorics.core.functional.FiniteArgumentFunction;
import org.tensorics.core.functional.Func1;
import org.tensorics.core.functional.Func2;
import org.tensorics.core.functional.Func3;
import org.tensorics.core.functional.Func4;
import org.tensorics.core.functional.Func5;
import org.tensorics.core.functional.Func6;
import org.tensorics.core.functional.Func7;
import org.tensorics.core.functional.Func8;
import org.tensorics.core.functional.Func9;
import org.tensorics.core.functional.FuncN;
import org.tensorics.core.functional.expressions.FunctionalExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

import com.google.common.collect.ImmutableList;

public final class TensoricExpressions {

    private TensoricExpressions() {
        /* Only static methods */
    }

    // TODO zero argument case?
    // TODO N argument case?

    public static <T> OngoingLambdaExpressionCreation<Func1<T, ?>> use(Expression<T> t1) {
        return new OngoingLambdaExpressionCreation<>(t1);
    }

    public static <T1, T2> OngoingLambdaExpressionCreation<Func2<T1, T2, ?>> use(Expression<T1> t1, Expression<T2> t2) {
        return new OngoingLambdaExpressionCreation<>(t1, t2);
    }

    public static <T1, T2, T3> OngoingLambdaExpressionCreation<Func3<T1, T2, T3, ?>> use(Expression<T1> t1,
            Expression<T2> t2, Expression<T3> t3) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3);
    }

    public static <T1, T2, T3, T4> OngoingLambdaExpressionCreation<Func4<T1, T2, T3, T4, ?>> use(Expression<T1> t1,
            Expression<T2> t2, Expression<T3> t3, Expression<T4> t4) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4);
    }

    public static <T1, T2, T3, T4, T5> OngoingLambdaExpressionCreation<Func5<T1, T2, T3, T4, T5, ?>> use(
            Expression<T1> t1, Expression<T2> t2, Expression<T3> t3, Expression<T4> t4, Expression<T5> t5) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4, t5);
    }

    public static <T1, T2, T3, T4, T5, T6> OngoingLambdaExpressionCreation<Func6<T1, T2, T3, T4, T5, T6, ?>> use(
            Expression<T1> t1, Expression<T2> t2, Expression<T3> t3, Expression<T4> t4, Expression<T5> t5,
            Expression<T6> t6) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4, t5, t6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> OngoingLambdaExpressionCreation<Func7<T1, T2, T3, T4, T5, T6, T7, ?>> use(
            Expression<T1> t1, Expression<T2> t2, Expression<T3> t3, Expression<T4> t4, Expression<T5> t5,
            Expression<T6> t6, Expression<T7> t7) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4, t5, t6, t7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> OngoingLambdaExpressionCreation<Func8<T1, T2, T3, T4, T5, T6, T7, T8, ?>> use(
            Expression<T1> t1, Expression<T2> t2, Expression<T3> t3, Expression<T4> t4, Expression<T5> t5,
            Expression<T6> t6, Expression<T7> t7, Expression<T8> t8) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> OngoingLambdaExpressionCreation<Func9<T1, T2, T3, T4, T5, T6, T7, T8, T9, ?>> use(
            Expression<T1> t1, Expression<T2> t2, Expression<T3> t3, Expression<T4> t4, Expression<T5> t5,
            Expression<T6> t6, Expression<T7> t7, Expression<T8> t8, Expression<T9> t9) {
        return new OngoingLambdaExpressionCreation<>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    public static class OngoingLambdaExpressionCreation<F extends FiniteArgumentFunction<?>> {
        private final List<Expression<?>> arguments;

        public OngoingLambdaExpressionCreation(Expression<?>... arguments) {
            this.arguments = ImmutableList.copyOf(arguments);
        }

        @SuppressWarnings("unchecked")
        public <R> Expression<R> in(F function) {
            if (arguments.size() != function.numberOfArgs()) {
                throw new IllegalStateException(format(
                        "The number of arguments for the function '%s' does not match the given arguments. "
                                + "Required %s but was %s. "
                                + "This should never happen, do not instantiate this class directly.",
                        function, function.numberOfArgs(), arguments.size()));
            }
            return new FunctionalExpression<>(arguments, (FuncN<R>) function.toFuncN());
        }

    }

    public static <T> Expression<T> lastOf(Expression<? extends Iterable<T>> source) {
        return LatestOfExpression.latestOf(source);
    }

    public static <T> OngoingBasicDeferredBinaryPredicate<T> testIf(Expression<T> left) {
        return new OngoingBasicDeferredBinaryPredicate<>(left);
    }

    public static <T> OngoingBasicDeferredBinaryPredicate<T> testIf(T left) {
        return testIf(ResolvedExpression.of(left));
    }

}
