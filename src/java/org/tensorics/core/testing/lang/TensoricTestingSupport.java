/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.TensoricSupport;

/**
 * Provides additional methods and fluent clauses for needs in testing tensorics. All the methods which require e.g. the
 * knowledge of field or similar aspects have to go in here. All the others (which are more structural) will be places
 * in {@link org.tensorics.core.testing.TensoricTests}.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values of the field which is used
 * @see org.tensorics.core.testing.TensoricTests
 */
public class TensoricTestingSupport<S> extends TensoricSupport<S> {

    private final Environment<S> environment;

    public TensoricTestingSupport(EnvironmentImpl<S> environment) {
        super(environment);
        this.environment = environment;
    }

    public OngoingCloseToMatcherCreation<S> within(S tolerance) {
        return new OngoingCloseToMatcherCreation<>(environment, tolerance);
    }

}
