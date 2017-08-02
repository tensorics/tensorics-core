/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static java.util.Objects.requireNonNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

final class ConditionedMapper<T, T1, R> implements BiFunction<T, Function<Object, R>, R> {

    private final Predicate<T> condition;
    private final BiFunction<? super T1, Function<Object, R>, R> mapper;
    private final Function<T, T1> conversion;

    public ConditionedMapper(Predicate<T> condition, Function<T, T1> conversion,
            BiFunction<? super T1, Function<Object, R>, R> mapper) {
        this.condition = requireNonNull(condition, "condition must not be null");
        this.conversion = requireNonNull(conversion, "conversion must not be null");
        this.mapper = requireNonNull(mapper, "mapper must not be null");
    }

    public Predicate<T> condition() {
        return condition;
    }

    @Override
    public R apply(T input, Function<Object, R> callback) {
        T1 converted = conversion.apply(input);
        return mapper.apply(converted, callback);
    }

}