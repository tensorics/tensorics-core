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

import org.tensorics.core.analysis.ConditionBuilder;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

public class OngoingAnalysisEnabler {

    private final ConditionBuilder enablerBuilder;

    public OngoingAnalysisEnabler(ConditionBuilder enablerBuilder) {
        this.enablerBuilder = requireNonNull(enablerBuilder, "builder must not be null");
    }

    public <T> OngoingCondition<T> when(Expression<T> source) {
        return new OngoingCondition<>(enablerBuilder, source);
    }

    public OngoingBooleanCondition whenBoolean(Expression<Boolean> source) {
        return new OngoingBooleanCondition(enablerBuilder, source);
    }

    public void always() {
        this.enablerBuilder.withName("always evaluated").withCondition(ResolvedExpression.of(true));
    }

}
