/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.lang;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.quantity.lang.QuantitySupport;
import org.tensorics.core.quantity.options.QuantityEnvironment;

import com.google.common.collect.Iterables;

/**
 * Contains methods of the tensorics eDSL which deal with iterables of quantities.
 * 
 * @author kfuchsbe
 * @param <V> the type of the scalar values of the quantities (elements of the field on which all the operations are
 *            based on)
 */
public class QuantityIterableSupport<V> extends QuantitySupport<V> {

    protected QuantityIterableSupport(QuantityEnvironment<V> environment) {
        super(environment);
    }

    public final QuantifiedValue<V> avarageOf(Iterable<QuantifiedValue<V>> values) {
        if (Iterables.isEmpty(values)) {
            throw new IllegalArgumentException("Averaging of empty value set is not possible.");
        }
        return calculate(sumOf(values)).dividedBy(sizeOf(values));
    }

    public final QuantifiedValue<V> sizeOf(Iterable<QuantifiedValue<V>> values) {
        QuantifiedValue<V> one = one();
        QuantifiedValue<V> count = zero();
        for (@SuppressWarnings("unused")
        Object value : values) {
            count = calculate(count).plus(one);
        }
        return count;
    }

    public final QuantifiedValue<V> sumOf(Iterable<QuantifiedValue<V>> values) {
        QuantifiedValue<V> sum = zero();
        for (QuantifiedValue<V> value : values) {
            sum = calculate(sum).plus(value);
        }
        return sum;
    }

    public final OngoingQuantityIterableValueExtraction<V> valuesOf(Iterable<QuantifiedValue<V>> quantities) {
        return new OngoingQuantityIterableValueExtraction<>(quantities, operationRepository());
    }

}
