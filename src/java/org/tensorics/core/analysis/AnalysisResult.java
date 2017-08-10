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

import com.google.common.collect.ImmutableList;

public class AnalysisResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<AssertionResult> assertionResults;
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
        private final ImmutableList.Builder<AssertionResult> resultsBuilder = ImmutableList.builder();

        Builder(AssertionStatus overallStatus) {
            this.overallStatus = requireNonNull(overallStatus, "overallStatus must not be null.");
        }

        public Builder add(AssertionResult result) {
            requireNonNull(result, "result to add must not be null.");
            resultsBuilder.add(result);
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

    public List<AssertionResult> assertionResults() {
        return this.assertionResults;
    }

    public List<AssertionExpression> assertions() {
        return this.assertionResults.stream().map(a -> a.assertion()).collect(Collectors.toList());
    }

    public AssertionStatus statusFor(AssertionExpression assertion) {
        for (AssertionResult assertionResult : this.assertionResults) {
            if (assertion.equals(assertionResult.assertion())) {
                return assertionResult.status();
            }
        }
        throw new NoSuchElementException("No result available for assertion'" + assertion + "'.");
    }

    public AssertionStatus overallStatus() {
        return overallStatus;
    }

}
