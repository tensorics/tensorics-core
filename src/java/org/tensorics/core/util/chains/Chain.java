/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Chain<R> implements Function<Object, R> {

    private final ImmutableRecursion<R> delegate;

    public Chain(BiFunction<Object, Function<Object, R>, R> delegate, Function<Object, R> endRecursionFunction,
            int endRecursionDepth) {
        this.delegate = ImmutableRecursion.delegatingTo(delegate).endingRecursionWith(endRecursionFunction)
                .endRecursionAtDepth(endRecursionDepth);
    }

    @Override
    public R apply(Object input) {
        return delegate.apply(input);
    }

    public R apply(Object input, int endRecursionDepth) {
        return delegate.endRecursionAtDepth(endRecursionDepth).apply(input);
    }

}