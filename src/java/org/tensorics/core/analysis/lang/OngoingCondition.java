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

import java.util.function.Predicate;

import org.tensorics.core.analysis.ConditionBuilder;
import org.tensorics.core.expressions.IsEqualToExpression;
import org.tensorics.core.expressions.IsNotEqualExpression;
import org.tensorics.core.expressions.PredicateExpression;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class OngoingCondition<T> extends OngoingNamedCondition<T> {

    public OngoingCondition(ConditionBuilder builder, Expression<T> source) {
        super(builder, source);
    }

    public OngoingNamedCondition<T> is(Expression<Predicate<T>> predicate) {
        builder.withCondition(PredicateExpression.ofSourceAndPredicate(source, predicate));
        return this;
    }

    public OngoingNamedCondition<T> is(Predicate<T> predicate) {
        is(ResolvedExpression.of(predicate));
        return this;
    }

    public OngoingNamedCondition<T> isEqualTo(Expression<T> other) {
        this.builder.withCondition(new IsEqualToExpression<>(source, other));
        return this;
    }

    public OngoingNamedCondition<T> isNotEqualTo(Expression<T> other) {
        this.builder.withCondition(new IsNotEqualExpression<>(source, other));
        return this;
    }

    public OngoingNamedCondition<T> isEqualTo(T other) {
        isEqualTo(ResolvedExpression.of(other));
        return this;
    }

    public OngoingNamedCondition<T> isNotEqualTo(T other) {
        isNotEqualTo(ResolvedExpression.of(other));
        return this;
    }

}
