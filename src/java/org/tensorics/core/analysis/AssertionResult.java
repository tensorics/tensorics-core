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

import java.io.Serializable;

import org.tensorics.core.analysis.expression.AssertionExpression;

public class AssertionResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private final AssertionExpression assertion;
    private final AssertionStatus status;

    private AssertionResult(AssertionExpression assertion, AssertionStatus status) {
        this.assertion = assertion;
        this.status = status;
    }

    public static AssertionResult of(AssertionExpression assertion, AssertionStatus status) {
        return new AssertionResult(assertion, status);
    }

    public String condition() {
        return assertion.name();
    }

    public AssertionStatus status() {
        return status;
    }

    public AssertionExpression assertion() {
        return this.assertion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assertion == null) ? 0 : assertion.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        AssertionResult other = (AssertionResult) obj;
        if (assertion == null) {
            if (other.assertion != null) {
                return false;
            }
        } else if (!assertion.equals(other.assertion)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AssertionResult [assertion=" + assertion + ", status=" + status + "]";
    }

}
