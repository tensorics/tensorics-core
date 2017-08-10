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

import java.util.Collection;
import java.util.List;

import org.tensorics.core.analysis.AnalysisResult;
import org.tensorics.core.analysis.resolver.AnalysisResolver;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;

import com.google.common.collect.ImmutableList;

/**
 * A group of assertions that are evaluated altogether. The strategy is defined in the {@link AnalysisResolver}.
 *
 * @see AnalysisResolver
 * @author acalia, caguiler, kfuchsberger
 */
public class AnalysisExpression extends AbstractDeferredExpression<AnalysisResult> {
    private static final long serialVersionUID = 1L;

    private final List<AssertionExpression> assertions;

    public AnalysisExpression(Collection<AssertionExpression> assertions) {
        this.assertions = ImmutableList.copyOf(assertions);
    }

    @Override
    public final List<AssertionExpression> getChildren() {
        return assertions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assertions == null) ? 0 : assertions.hashCode());
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
        AnalysisExpression other = (AnalysisExpression) obj;
        if (assertions == null) {
            if (other.assertions != null) {
                return false;
            }
        } else if (!assertions.equals(other.assertions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssertionGroupExpression [assertions=" + assertions + "]";
    }

}
