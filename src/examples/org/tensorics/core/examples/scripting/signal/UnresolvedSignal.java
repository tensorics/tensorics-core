// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.examples.scripting.signal;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.base.Preconditions;

/**
 * @author kfuchsbe
 */
public class UnresolvedSignal extends AbstractDeferredExpression<DiscreteFunction<Double, Double>> {

    private final String signalName;

    public static UnresolvedSignal ofName(String signalName) {
        return new UnresolvedSignal(signalName);
    }

    private UnresolvedSignal(String signalName) {
        super();
        this.signalName = Preconditions.checkNotNull(signalName, "signalName must not be null");
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((signalName == null) ? 0 : signalName.hashCode());
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
        UnresolvedSignal other = (UnresolvedSignal) obj;
        if (signalName == null) {
            if (other.signalName != null) {
                return false;
            }
        } else if (!signalName.equals(other.signalName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return signalName;
    }

}