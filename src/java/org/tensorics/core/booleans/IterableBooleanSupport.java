package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingBooleanIterableOperation;

public class IterableBooleanSupport {

    public OngoingBooleanIterableOperation calcLogical(Iterable<Boolean> iterable) {
        return new OngoingBooleanIterableOperation(iterable);
    }

}
