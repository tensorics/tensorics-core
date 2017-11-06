/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.lang;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.commons.options.Environment;

public class OngoingFieldAwareTensorReduction<V> extends OngoingTensorReduction<V> {

    private final Environment<V> environment;

    public OngoingFieldAwareTensorReduction(Environment<V> environment) {
        this.environment = requireNonNull(environment, "environment must not be null");
    }

}
