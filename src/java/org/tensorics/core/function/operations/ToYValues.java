/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.operations;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.MathFunctions;

public class ToYValues<Y> implements Conversion<DiscreteFunction<?, Y>, Iterable<Y>> {

    @Override
    public Iterable<Y> perform(DiscreteFunction<?, Y> inputfunction) {
        return MathFunctions.yValuesOf(inputfunction);
    }

}
