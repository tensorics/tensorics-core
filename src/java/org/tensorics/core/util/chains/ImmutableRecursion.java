/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;

final class ImmutableRecursion<R> implements Function<Object, R> {

    private final Set<Object> ongoingQueries;
    private final BiFunction<Object, Function<Object, R>, R> delegate;
    private final int endRecursionDepth;
    private final Function<Object, R> endRecursionFunction;

    public ImmutableRecursion(BiFunction<Object, Function<Object, R>, R> delegate, int maxRecursionDepth,
            Set<Object> ongoingQueries, Function<Object, R> endRecursionFunction) {
        super();
        this.delegate = requireNonNull(delegate, "delegate must not be null");
        this.ongoingQueries = requireNonNull(ongoingQueries, "ongoingQueries must not be null");
        checkArgument(maxRecursionDepth >= 0, "endRecursionDepth must be positive, but was {}.", maxRecursionDepth);
        this.endRecursionDepth = maxRecursionDepth;
        this.endRecursionFunction = requireNonNull(endRecursionFunction, "endRecursionFunction must not be null");
    }

    static final <R> ImmutableRecursion<R> delegatingTo(BiFunction<Object, Function<Object, R>, R> newDelegate) {
        return new ImmutableRecursion<>(newDelegate, Integer.MAX_VALUE, Collections.emptySet(), (o) -> null);
    }

    final ImmutableRecursion<R> endRecursionAtDepth(int newMaxRecursionDepth) {
        return new ImmutableRecursion<>(delegate, newMaxRecursionDepth, ongoingQueries, endRecursionFunction);
    }

    final ImmutableRecursion<R> endingRecursionWith(Function<Object, R> newRndRecursionFunction) {
        return new ImmutableRecursion<>(delegate, endRecursionDepth, ongoingQueries, newRndRecursionFunction);
    }

    final ImmutableRecursion<R> withAdditionalQuery(Object o) {
        Set<Object> newQueries = ImmutableSet.builder().addAll(ongoingQueries).add(o).build();
        return new ImmutableRecursion<>(delegate, endRecursionDepth, newQueries, endRecursionFunction);
    }

    @Override
    public R apply(Object t) {
        if (ongoingQueries.contains(t)) {
            throw new RuntimeException("Loop detected in recursive calls. Object '" + t
                    + "' was queried again, while the following queries were ongoing: " + ongoingQueries + ".");
        }
        if ((actualRecursionDepth()) >= endRecursionDepth) {
            return endRecursionFunction.apply(t);
        }
        return delegate.apply(t, this.withAdditionalQuery(t));
    }

    private int actualRecursionDepth() {
        return ongoingQueries.size();
    }

}