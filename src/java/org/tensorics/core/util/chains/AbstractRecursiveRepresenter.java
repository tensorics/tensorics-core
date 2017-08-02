/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import java.util.function.Function;

public abstract class AbstractRecursiveRepresenter<R> {

    /* This has to be package private, because it is injected by the framework */
    Function<Object, R> recursion;

    protected R recurse(Object object) {
        return this.recursion.apply(object);
    }

}
