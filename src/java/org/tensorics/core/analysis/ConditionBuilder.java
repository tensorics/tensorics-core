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

import org.tensorics.core.tree.domain.Expression;

public class ConditionBuilder {

    private String name;
    private Expression<Boolean> condition;
    private String key;

    public ConditionBuilder withName(String newName) {
        this.name = requireNonNull(newName, "name must not be null");
        return this;
    }

    public <T extends ConditionBuilder> T withCondition(Expression<Boolean> newCondition) {
        requireNonNull(newCondition, "condition must not be null");
        this.condition = newCondition;
        @SuppressWarnings("unchecked")
        T conditionBuilderOrSubclass = (T) this;
        return conditionBuilderOrSubclass;
    }

    public String name() {
        return name;
    }

    public Expression<Boolean> condition() {
        return condition;
    }

    public ConditionBuilder withKey(String newKey) {
        this.key = requireNonNull(newKey, "key must not be null");
        return this;
    }

    public String key() {
        return key;
    }

}