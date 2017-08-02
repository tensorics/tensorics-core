/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static java.util.Objects.requireNonNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

abstract class AbstractChainBuilder<T, R, F, CB extends AbstractChainBuilder<T, R, F, CB>> {

    final ImmutableList.Builder<ConditionedMapper<T, ?, R>> mappers = ImmutableList.builder();
    boolean throwIfAllReturnNull;
    R defaultValue;

    @SuppressWarnings("unchecked")
    private CB castedThis = (CB) this;

    void addMapper(Function<? super T, R> mapper) {
        requireNonNull(mapper, "new mapper must not be null");
        addMapper((o, c) -> mapper.apply(o));
    }

    void addMapper(BiFunction<? super T, Function<Object, R>, R> mapperWithCallback) {
        addConditionedMapper(o -> true, Function.identity(), mapperWithCallback);
    }

    <T1 extends T> void addMapper(Predicate<T> condition, Function<T, T1> conv, Function<T1, R> mapper) {
        requireNonNull(mapper, "mapper must not be null");
        addConditionedMapper(condition, conv, (o, c) -> mapper.apply(o));
    }

    <T1> CB addConditionedMapper(Predicate<T> condition, Function<T, T1> conv,
            BiFunction<? super T1, Function<Object, R>, R> mapperWithCallback) {
        requireNonNull(mapperWithCallback, "new mapper must not be null");
        mappers.add(new ConditionedMapper<>(condition, conv, mapperWithCallback));
        return castedThis;
    }

    CB orElseThrow() {
        this.defaultValue = null;
        this.throwIfAllReturnNull = true;
        return castedThis;
    }

    CB orElseNull() {
        this.defaultValue = null;
        this.throwIfAllReturnNull = false;
        return castedThis;
    }

    CB orElse(R returnValue) {
        this.defaultValue = returnValue;
        this.throwIfAllReturnNull = false;
        return castedThis;
    }

    abstract F build();

}