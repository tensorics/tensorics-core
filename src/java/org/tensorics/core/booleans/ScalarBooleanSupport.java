package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingBooleanScalarOperation;

/**
 * Support class for the Scalar boolean operations<br>
 * <b>Note:</b> Use through the delegation class {@link BooleanSupport}
 * 
 * @author agorzaws
 */
public class ScalarBooleanSupport {

    public OngoingBooleanScalarOperation calcLogical(Boolean left) {
        return new OngoingBooleanScalarOperation(left);
    }

}
