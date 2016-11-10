package org.tensorics.core.booleans;

import org.tensorics.core.booleans.lang.OngoingDetection;
import org.tensorics.core.booleans.lang.OngoingIterableBooleanAlgebra;

public class IterableBooleanSupport {

    public OngoingIterableBooleanAlgebra calcLogical(Iterable<Boolean> iterable) {
        return new OngoingIterableBooleanAlgebra(iterable);
    }

    public OngoingDetection detect() {
        return new OngoingDetection();
    }

}
