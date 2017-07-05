package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingBooleanIterableOperation;

/**
 * Support class for the Iterable boolean objects. <br>
 * <b>Note:</b> Use through the delegation class {@link BooleanSupport}
 * 
 * @author agorzaws
 */
public class IterableBooleanSupport {

    public OngoingBooleanIterableOperation calcLogical(Iterable<Boolean> iterable) {
        return new OngoingBooleanIterableOperation(iterable);
    }

}
