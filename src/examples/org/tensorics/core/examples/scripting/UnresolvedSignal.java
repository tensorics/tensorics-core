/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples.scripting;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

import com.google.common.base.Preconditions;

public class UnresolvedSignal extends AbstractDeferredExpression<Iterable<Double>> {

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

}