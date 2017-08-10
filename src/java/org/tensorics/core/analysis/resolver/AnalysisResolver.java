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

package org.tensorics.core.analysis.resolver;

import static org.tensorics.core.analysis.AssertionStatus.NONAPPLICABLE;
import static org.tensorics.core.analysis.AssertionStatus.SUCCESSFUL;
import static org.tensorics.core.analysis.AssertionStatus.fromBooleanSuccessful;
import static org.tensorics.core.analysis.util.Predicates.not;
import static org.tensorics.core.resolve.resolvers.Resolvers.contextResolvesAll;

import org.tensorics.core.analysis.AnalysisResult;
import org.tensorics.core.analysis.AnalysisResult.Builder;
import org.tensorics.core.analysis.AssertionResult;
import org.tensorics.core.analysis.AssertionStatus;
import org.tensorics.core.analysis.expression.AnalysisExpression;
import org.tensorics.core.analysis.expression.AssertionExpression;
import org.tensorics.core.resolve.resolvers.AbstractResolver;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Resolves an {@link AnalysisExpression} into a {@link AssertionStatus}.
 * <p>
 * The status will be {@link AssertionStatus#SUCCESSFUL} if only if all the assertions in the group are
 * {@link AssertionStatus#SUCCESSFUL}. Otherwise, the status will be {@link AssertionStatus#FAILURE}.
 *
 * @see AnalysisExpression
 * @author acalia, caguiler, kfuchsberger
 */
public class AnalysisResolver extends AbstractResolver<AnalysisResult, AnalysisExpression> {

    @Override
    public boolean canResolve(AnalysisExpression assertionSet, ResolvingContext context) {
        return contextResolvesAll(assertionSet.getChildren(), context);
    }

    @Override
    public AnalysisResult resolve(AnalysisExpression assertionSet, ResolvingContext context) {
        AssertionStatus overallStatus = overallStatus(assertionSet, context);
        Builder builder = AnalysisResult.builder(overallStatus);
        for (AssertionExpression assertion : assertionSet.getChildren()) {
            AssertionResult assertionResult = context.resolvedValueOf(assertion);
            builder.put(assertion, assertionResult);
        }
        return builder.build();
    }

    private AssertionStatus overallStatus(AnalysisExpression assertionSet, ResolvingContext context) {
        return fromBooleanSuccessful(assertionSet.getChildren().stream().map(context::resolvedValueOf)
                .map(AssertionResult::status).filter(not(NONAPPLICABLE::equals)).allMatch(SUCCESSFUL::equals));
    }

}
