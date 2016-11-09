/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

public class OngoingIterableBooleanDetection {

    private Iterable<Boolean> iterable;

    public OngoingIterableBooleanDetection(Iterable<Boolean> iterable) {
        this.iterable = iterable;
    }

    // TODO should be index of the change? or the case is not valid at all? (only later interesting for
    // tensors/functions)
    public Integer changes() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
