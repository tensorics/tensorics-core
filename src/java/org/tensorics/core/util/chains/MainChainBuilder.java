/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

final class MainChainBuilder<R> extends AbstractChainBuilder<Object, R, Chain<R>, MainChainBuilder<R>> {

    private Function<Object, R> endRecursionFunction = o -> null;
    private int defaultEndRecursionDepth = 6;

    public MainChainBuilder<R> endRecursionWith(Function<Object, R> newEndRecursionFunction) {
        this.endRecursionFunction = requireNonNull(newEndRecursionFunction, "endRecursionFuntion must not be null");
        return this;
    }

    public MainChainBuilder<R> endRecursionAtDepth(int newDefaultEndRecursionDepth) {
        this.defaultEndRecursionDepth = newDefaultEndRecursionDepth;
        return this;
    }

    @Override
    Chain<R> build() {
        return new Chain<>(new Branch<Object, R>(this), endRecursionFunction, defaultEndRecursionDepth);
    }

}