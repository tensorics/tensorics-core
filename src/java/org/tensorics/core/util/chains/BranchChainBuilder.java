/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

final class BranchChainBuilder<T, R> extends AbstractChainBuilder<T, R, Branch<T, R>, BranchChainBuilder<T, R>> {

    @Override
    Branch<T, R> build() {
        return new Branch<>(this);
    }

}