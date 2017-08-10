// @formatter:off
/**
*
* This file is part of streaming pool (http://www.streamingpool.org).
* 
* Copyright (c) 2017-present, CERN. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
*/
// @formatter:on

package org.tensorics.core.analysis.lang;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.tensorics.core.analysis.AssertionBuilder;
import org.tensorics.core.booleans.operations.Or;
import org.tensorics.core.expressions.IsEqualToExpression;
import org.tensorics.core.expressions.LatestOfExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class OngoingPrecondition<T> {

    private final AssertionBuilder builder;
    private final Expression<T> source;

    public OngoingPrecondition(AssertionBuilder builder, Expression<T> whenSource) {
        this.builder = requireNonNull(builder, "builder must not be null");
        this.source = requireNonNull(whenSource, "source must not be null");
    }

    public OngoingPrecondition(AssertionBuilder builder, T source) {
        this(builder, ResolvedExpression.of(source));
    }

    public OngoingPrecondition<T> isEqualTo(Expression<T> other) {
        this.builder.withPreCondition(new IsEqualToExpression<>(source, other));
        return this;
    }

    public OngoingPrecondition<T> isEqualTo(T other) {
        return isEqualTo(ResolvedExpression.of(other));
    }

    public OngoingPrecondition<T> or() {
        this.builder.withPreConditionReducer(new Or());
        return this;
    }

    public final <T1> OngoingCondition<T1> thenAssertThat(Expression<T1> thatSource) {
        return new OngoingCondition<>(builder, thatSource);
    }

    public final <T1> OngoingCondition<T1> thenAssertThat(T1 thatSource) {
        return thenAssertThat(ResolvedExpression.of(thatSource));
    }

    public final OngoingBooleanCondition thenAssertBoolean(Expression<Boolean> thatSource) {
        return new OngoingBooleanCondition(builder, thatSource);
    }

    public final OngoingAllBooleanExcludableCondition thenAssertAllBoolean(
            Set<? extends Expression<Boolean>> thatSource) {
        return new OngoingAllBooleanExcludableCondition(builder, thatSource);
    }

    public final OngoingAnyBooleanCondition thenAssertAtLeastOneBooleanOf(
            Expression<? extends Iterable<Boolean>> thatSource) {
        return new OngoingAnyBooleanCondition(builder, thatSource);
    }

    public final OngoingBooleanCondition thenAssertBoolean(Boolean thatSource) {
        return thenAssertBoolean(ResolvedExpression.of(thatSource));
    }

    public OngoingBooleanCondition thenAssertLatestBooleanOf(Expression<? extends Iterable<Boolean>> buffered) {
        return new OngoingBooleanCondition(builder, LatestOfExpression.latestOf(buffered));
    }

}
