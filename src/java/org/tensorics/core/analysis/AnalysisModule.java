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

package org.tensorics.core.analysis;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tensorics.core.analysis.expression.AnalysisExpression;
import org.tensorics.core.analysis.expression.AssertionExpression;
import org.tensorics.core.analysis.lang.OngoingAllBooleanCondition;
import org.tensorics.core.analysis.lang.OngoingAllBooleanExcludableCondition;
import org.tensorics.core.analysis.lang.OngoingAnyBooleanCondition;
import org.tensorics.core.analysis.lang.OngoingBooleanCondition;
import org.tensorics.core.analysis.lang.OngoingCondition;
import org.tensorics.core.analysis.lang.OngoingPrecondition;
import org.tensorics.core.booleans.operations.And;
import org.tensorics.core.expressions.LatestOfExpression;
import org.tensorics.core.iterable.expressions.IterableExpressionToIterable;
import org.tensorics.core.iterable.expressions.IterableOperationExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

/**
 * Abstract base class for analysis modules. Provides fluent API methods to specify assertions.
 * <p>
 * This class is not threadsafe!
 *
 * @author acalia, caguiler, kfuchsbe, mhruska
 */
public abstract class AnalysisModule {

    private final List<AssertionBuilder> assertionBuilders = new ArrayList<>();

    protected final <T1> OngoingCondition<T1> assertThat(Expression<T1> thatSource) {
        return new OngoingCondition<>(newAssertionBuilder(), thatSource);
    }

    protected final <T1> OngoingCondition<T1> assertThat(T1 thatSource) {
        return assertThat(ResolvedExpression.of(thatSource));
    }

    protected final OngoingBooleanCondition assertBoolean(Expression<Boolean> thatSource) {
        return new OngoingBooleanCondition(newAssertionBuilder(), thatSource);
    }

    protected final OngoingBooleanCondition assertBoolean(Boolean thatSource) {
        return assertBoolean(ResolvedExpression.of(thatSource));
    }

    protected final OngoingAllBooleanExcludableCondition assertAllBoolean(
            Set<? extends Expression<Boolean>> thatSource) {
        return new OngoingAllBooleanExcludableCondition(newAssertionBuilder(), thatSource);
    }

    protected final OngoingAllBooleanCondition assertAllBoolean(Expression<? extends Iterable<Boolean>> thatSource) {
        return new OngoingAllBooleanCondition(newAssertionBuilder(), thatSource);
    }

    protected final OngoingAnyBooleanCondition assertAtLeastOneBooleanOf(
            Expression<? extends Iterable<Boolean>> thatSource) {
        return new OngoingAnyBooleanCondition(newAssertionBuilder(), thatSource);
    }

    protected OngoingBooleanCondition assertLatestBooleanOf(Expression<? extends Iterable<Boolean>> buffered) {
        return new OngoingBooleanCondition(newAssertionBuilder(), LatestOfExpression.latestOf(buffered));
    }

    protected final OngoingPrecondition<Boolean> whenTrue(Expression<Boolean> whenSource) {
        return new OngoingPrecondition<>(newAssertionBuilder(), whenSource).isEqualTo(true);
    }

    protected final OngoingPrecondition<Boolean> whenFalse(Expression<Boolean> whenSource) {
        return new OngoingPrecondition<>(newAssertionBuilder(), whenSource).isEqualTo(false);
    }

    protected final OngoingPrecondition<Boolean> whenNot(Expression<Boolean> whenSource) {
        return whenFalse(whenSource);
    }

    protected final OngoingPrecondition<Boolean> whenAllTrue(Iterable<Expression<Boolean>> expressions) {
        Expression<Iterable<Boolean>> booleans = new IterableExpressionToIterable<>(expressions);
        Expression<Boolean> combined = new IterableOperationExpression<>(new And(), booleans);
        return whenTrue(combined);
    }

    protected final <T1> OngoingPrecondition<T1> when(Expression<T1> whenSource) {
        return new OngoingPrecondition<>(newAssertionBuilder(), whenSource);
    }

    protected final <T1> OngoingPrecondition<T1> when(T1 whenSource) {
        return when(ResolvedExpression.of(whenSource));
    }

    private AssertionBuilder newAssertionBuilder() {
        AssertionBuilder builder = new AssertionBuilder();
        assertionBuilders.add(builder);
        return builder;
    }

    public List<AssertionBuilder> assertionBuilders() {
        return assertionBuilders;
    }

    public AnalysisExpression buildExpression() {
        return assertionBuilders().stream().map(AssertionExpression::new)
                .collect(collectingAndThen(toList(), AnalysisExpression::new));
    }

}
