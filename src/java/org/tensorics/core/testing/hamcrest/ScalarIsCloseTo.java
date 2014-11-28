/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.tensorics.core.scalar.lang.ScalarSupport;

public class ScalarIsCloseTo<S> extends TypeSafeMatcher<S> {

    private final ScalarSupport<S> support;
    private final S value;
    private final S tolerance;

    public ScalarIsCloseTo(ScalarSupport<S> scalarSupport, S value, S tolerance) {
        super();
        this.support = scalarSupport;
        this.value = value;
        this.tolerance = tolerance;
    }

    @Override
    public void describeTo(Description description) {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean matchesSafely(S valueToAssert) {
        S diff = support.calculate(valueToAssert).minus(value);
        S absoluteDiff = support.absoluteValueOf(diff);
        // TODO Auto-generated method stub
        return false;
    }

}
