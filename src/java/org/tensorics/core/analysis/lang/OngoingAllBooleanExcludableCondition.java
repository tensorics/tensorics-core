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

import java.util.Set;

import org.tensorics.core.analysis.ConditionBuilder;
import org.tensorics.core.booleans.operations.And;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.iterable.expressions.IterableExpressionToIterable;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class OngoingAllBooleanExcludableCondition {

    private static final And ALL_TRUE = new And();
    private final ConditionBuilder builder;
    private final Set<? extends Expression<Boolean>> sources;

    public OngoingAllBooleanExcludableCondition(ConditionBuilder builder, Set<? extends Expression<Boolean>> source) {
        super();
        this.builder = builder;
        this.sources = source;
    }

    @SafeVarargs
    public final OngoingAllBooleanExcludableCondition excluding(Expression<Boolean>... excludedExpressions) {
        return excluding(Sets.newHashSet(excludedExpressions));
    }

    public OngoingAllBooleanExcludableCondition excluding(Set<Expression<Boolean>> excludedExpressions) {
        return new OngoingAllBooleanExcludableCondition(builder,
                ImmutableSet.copyOf(Sets.difference(sources, excludedExpressions)));
    }

    public OngoingAllBooleanExcludableCondition withName(String name) {
        this.builder.withName(name);
        return this;
    }

    public OngoingAllBooleanExcludableCondition withKey(String key) {
        this.builder.withKey(key);
        return this;
    }

    public OngoingAllBooleanExcludableCondition areTrue() {
        this.builder.withCondition(new ConversionOperationExpression<>(ALL_TRUE,
                new IterableExpressionToIterable<>(sources)));
        return this;
    }

}
