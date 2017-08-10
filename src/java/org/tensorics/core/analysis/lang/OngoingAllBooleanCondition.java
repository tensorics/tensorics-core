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

import org.tensorics.core.analysis.ConditionBuilder;
import org.tensorics.core.booleans.operations.And;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tree.domain.Expression;

public class OngoingAllBooleanCondition {

    private static final And ALL_TRUE = new And();
    private final ConditionBuilder builder;
    private final Expression<Iterable<Boolean>> sources;

    public OngoingAllBooleanCondition(ConditionBuilder builder, Expression<? extends Iterable<Boolean>> source) {
        super();
        this.builder = builder;
        @SuppressWarnings("unchecked")
        /* XXX this is not too nice... is there any better way? */
        Expression<Iterable<Boolean>> castedSource = (Expression<Iterable<Boolean>>) source;
        this.sources = castedSource;
    }

    public OngoingAllBooleanCondition areTrue() {
        this.builder.withCondition(new ConversionOperationExpression<>(ALL_TRUE, sources));
        return this;
    }

    public OngoingAllBooleanCondition withName(String name) {
        this.builder.withName(name);
        return this;
    }

    public OngoingAllBooleanCondition withKey(String key) {
        this.builder.withKey(key);
        return this;
    }

}
