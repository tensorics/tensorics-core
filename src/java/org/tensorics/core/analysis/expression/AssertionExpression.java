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

package org.tensorics.core.analysis.expression;

import static java.util.Objects.requireNonNull;
import static org.tensorics.core.analysis.AssertionStatus.ERROR;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorics.core.analysis.AssertionBuilder;
import org.tensorics.core.analysis.AssertionResult;
import org.tensorics.core.analysis.AssertionStatus;
import org.tensorics.core.analysis.resolver.AssertionResolver;
import org.tensorics.core.expressions.IterableResolvingExpression;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.ExceptionHandlingNode;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;

import com.google.common.collect.ImmutableList;

/**
 * {@link Expression} that resolves to a {@link AssertionStatus}. An {@link AssertionExpression} is composed by a
 * condition expression and a precondition expression. The precondition specifies if it makes sense to evaluate the
 * {@link AssertionExpression}.
 *
 * @see AssertionResolver
 * @author acalia, caguiler, kfuchsbe
 */
public class AssertionExpression extends AbstractDeferredExpression<AssertionResult>
        implements ExceptionHandlingNode<AssertionResult> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssertionExpression.class);
    private static final Expression<Boolean> DEFAULT_TRUE_PRECONDITION = ResolvedExpression.of(true);

    private final String name;
    private final String key;
    private final Expression<Boolean> condition;
    private final Expression<Boolean> preConditionsExpression;

    public AssertionExpression(AssertionBuilder builder) {
        requireNonNull(builder.condition(), "conditions must not be null");
        requireNonNull(builder.preConditions(), "preconditions must not be null");
        requireNonNull(builder.preConditionsReducer(), "preConditionsCollector must not be null");
        this.name = builder.name();
        this.condition = builder.condition();
        this.key = builder.key();
        this.preConditionsExpression = new PreconditionExpression(builder.preConditionsReducer(),
                new IterableResolvingExpression<>(defaultPreconditionOnEmpty(builder.preConditions())));
    }

    @Override
    public List<Expression<Boolean>> getChildren() {
        return ImmutableList.of(condition, preConditionsExpression);
    }

    public Expression<Boolean> condition() {
        return this.condition;
    }

    public Expression<Boolean> preConditionsExpression() {
        return this.preConditionsExpression;
    }

    public String name() {
        return this.name;
    }

    @Override
    public AssertionResult handle(Exception exception) {
        LOGGER.error("Exception evaluating expression {} [{}]", name, key, exception);
        return AssertionResult.of(ERROR);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result + ((key() == null) ? 0 : key().hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((preConditionsExpression == null) ? 0 : preConditionsExpression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AssertionExpression other = (AssertionExpression) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (key() == null) {
            if (other.key() != null) {
                return false;
            }
        } else if (!key().equals(other.key())) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (preConditionsExpression == null) {
            if (other.preConditionsExpression != null) {
                return false;
            }
        } else if (!preConditionsExpression.equals(other.preConditionsExpression)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssertionExpression [name=" + name + ", key=" + key() + ", condition=" + condition
                + ", preConditionsExpression=" + preConditionsExpression + "]";
    }

    public String key() {
        return key;
    }

    private static Collection<Expression<Boolean>> defaultPreconditionOnEmpty(
            Collection<Expression<Boolean>> preConditions) {
        return preConditions.isEmpty() ? Collections.singletonList(DEFAULT_TRUE_PRECONDITION) : preConditions;
    }

}
