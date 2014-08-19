/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.reduction;

import java.util.Map;

import org.tensorics.core.iterable.lang.ScalarIterableSupport;
import org.tensorics.core.math.ExtendedField;

/**
 * @author kfuchsbe
 * @param <V> the type of the elements of the field.
 */
public class Averaging<V> extends ScalarIterableSupport<V> implements ReductionStrategy<Object, V> {

    public Averaging(ExtendedField<V> field) {
        super(field);
    }

    @Override
    public V reduce(Map<?, V> inputValues) {
        return avarageOf(inputValues.values());
    }

}
