/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

final class Branch<T, R> implements BiFunction<T, Function<Object, R>, R> {

    private final List<ConditionedMapper<T, ?, R>> conditionedMappers;
    private final R defaultValue;
    private final boolean throwIfAllReturnNull;

    Branch(AbstractChainBuilder<T, R, ?, ?> builder) {
        this.conditionedMappers = builder.mappers.build();
        this.defaultValue = builder.defaultValue;
        this.throwIfAllReturnNull = builder.throwIfAllReturnNull;
    }

    @Override
    public R apply(T input, Function<Object, R> callback) {
        requireNonNull(input, "input value to chained functions must not be null.");
        for (ConditionedMapper<T, ?, R> conditionedMapper : conditionedMappers) {
            if (conditionedMapper.condition().test(input)) {
                /* The callback is always passed through to the branches */
                R returnValue = conditionedMapper.apply(input, callback);
                if (returnValue != null) {
                    return returnValue;
                }
            }
        }
        if (throwIfAllReturnNull) {
            throw new IllegalArgumentException(
                    "None of the chained functions returned a non-null value for input value '" + input + "'.");
        }
        return defaultValue;
    }

}