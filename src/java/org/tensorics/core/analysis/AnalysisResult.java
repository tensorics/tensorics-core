/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.analysis;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.tensorics.core.analysis.expression.AssertionExpression;

import com.google.common.collect.ImmutableMap;

public class AnalysisResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /* We on purpose we use a guava immutable map here, because it preserves insertion order */
    private final ImmutableMap<AssertionExpression, AssertionResult> assertionResults;
    private final AssertionStatus overallStatus;

    public AnalysisResult(Builder builder) {
        this.overallStatus = builder.overallStatus;
        this.assertionResults = builder.resultsBuilder.build();
    }

    public static final Builder builder(AssertionStatus overallStatus) {
        return new Builder(overallStatus);
    }

    public static final class Builder {

        private final AssertionStatus overallStatus;
        private final ImmutableMap.Builder<AssertionExpression, AssertionResult> resultsBuilder = ImmutableMap
                .builder();

        Builder(AssertionStatus overallStatus) {
            this.overallStatus = requireNonNull(overallStatus, "overallStatus must not be null.");
        }

        public Builder put(AssertionExpression expression, AssertionResult result) {
            requireNonNull(expression, "expression to add must not be null.");
            requireNonNull(result, "result to add must not be null.");
            resultsBuilder.put(expression, result);
            return this;
        }

        public AnalysisResult build() {
            return new AnalysisResult(this);
        }
    }

    @Override
    public String toString() {
        return "AnalysisResult [overallStatus=" + overallStatus + ", assertionResults=" + assertionResults + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assertionResults == null) ? 0 : assertionResults.hashCode());
        result = prime * result + ((overallStatus == null) ? 0 : overallStatus.hashCode());
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
        AnalysisResult other = (AnalysisResult) obj;
        if (assertionResults == null) {
            if (other.assertionResults != null) {
                return false;
            }
        } else if (!assertionResults.equals(other.assertionResults)) {
            return false;
        }
        if (overallStatus != other.overallStatus) {
            return false;
        }
        return true;
    }

    public List<AssertionExpression> assertions() {
        return this.assertionResults.keySet().stream().collect(Collectors.toList());
    }

    public AssertionStatus statusFor(AssertionExpression assertion) {
        return resultFor(assertion).status();

    }

    public AssertionResult resultFor(AssertionExpression assertion) {
        if (!assertionResults.containsKey(assertion)) {
            throw new NoSuchElementException("No result available for assertion'" + assertion + "'.");
        }
        return assertionResults.get(assertion);
    }

    public AssertionStatus overallStatus() {
        return overallStatus;
    }

}
