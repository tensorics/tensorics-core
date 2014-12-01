/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.lang;

import org.hamcrest.Matcher;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.testing.hamcrest.ScalarIsCloseTo;

/**
 * Part of a fluent clause to create a matcher which will allow to check if some value is 'close to' some other value.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar for which a matcher has to be created
 */
public class OngoingScalarCloseToMatcherCreation<S> {

    private final ExtendedField<S> field;
    private final S tolerance;

    public OngoingScalarCloseToMatcherCreation(ExtendedField<S> field, S tolerance) {
        super();
        this.field = field;
        this.tolerance = tolerance;
    }

    public Matcher<S> closeTo(S value) {
        return new ScalarIsCloseTo<S>(field, value, tolerance);
    }

}
