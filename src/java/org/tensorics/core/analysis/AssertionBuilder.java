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

import static java.util.Objects.requireNonNull;

import java.util.Collection;

import org.tensorics.core.analysis.lang.IterableBooleanConversion;
import org.tensorics.core.booleans.operations.And;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableList;

public class AssertionBuilder extends ConditionBuilder {
    private static final IterableBooleanConversion DEFAULT_CONDITIONS_REDUCER = new And();

    private final ImmutableList.Builder<Expression<Boolean>> preConditions = ImmutableList.builder();
    private IterableBooleanConversion preConditionsReducer = DEFAULT_CONDITIONS_REDUCER;

    public ConditionBuilder withPreConditionReducer(IterableBooleanConversion newPreConditionsConversion) {
        requireNonNull(newPreConditionsConversion, "preConditionsConversion must not be null");
        this.preConditionsReducer = newPreConditionsConversion;
        return this;
    }

    public ConditionBuilder withPreCondition(Expression<Boolean> preCondition) {
        requireNonNull(preCondition, "preCondition must not be null");
        this.preConditions.add(preCondition);
        return this;
    }

    public Collection<Expression<Boolean>> preConditions() {
        return preConditions.build();
    }

    public IterableBooleanConversion preConditionsReducer() {
        return preConditionsReducer;
    }

}
