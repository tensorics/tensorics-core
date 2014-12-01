/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.lang;

import org.hamcrest.Matcher;
import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.scalar.lang.ScalarSupport;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorSupport;
import org.tensorics.core.testing.hamcrest.ScalarIsCloseTo;
import org.tensorics.core.testing.hamcrest.TensorIsCloseTo;

/**
 * Part of a fluent clause to create a matcher which will allow to check if some value is 'close to' some other value.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar for which a matcher has to be created
 */
public class OngoingCloseToMatcherCreation<S> {

    private final Environment<S> environment;
    private final S tolerance;

    public OngoingCloseToMatcherCreation(Environment<S> environment, S tolerance) {
        super();
        this.environment = environment;
        this.tolerance = tolerance;
    }

    public Matcher<S> closeTo(S value) {
        return new ScalarIsCloseTo<S>(new ScalarSupport<>(environment.field()), value, tolerance);
    }

    public Matcher<Tensor<S>> closeTo(Tensor<S> tensor) {
        return new TensorIsCloseTo<S>(new TensorSupport<>(environment), tensor, tolerance);
    }
}
