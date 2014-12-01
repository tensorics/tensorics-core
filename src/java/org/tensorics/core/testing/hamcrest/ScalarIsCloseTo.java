/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.hamcrest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.tensorics.core.scalar.lang.ScalarSupport;

/**
 * A matcher for unit tests, that allows to set a tolerance value. This matcher uses the scalar support of tensorics,
 * thus it can be used on any scalars fro which a field is provided.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalars (elements of the field)
 */
public class ScalarIsCloseTo<S> extends TypeSafeMatcher<S> {

    private final ScalarSupport<S> support;
    private final S value;
    private final S tolerance;

    public ScalarIsCloseTo(ScalarSupport<S> scalarSupport, S value, S tolerance) {
        super();
        this.support = checkNotNull(scalarSupport, "scalarSupport must not be null");
        this.value = checkNotNull(value, "value must not be null");
        this.tolerance = checkNotNull(tolerance, "tolerance must not be null");
        checkArgument(support.testIf(tolerance).isGreaterOrEqualTo(support.zero()), "Tolerance must be positive.");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("within " + tolerance + " close to " + value);
    }

    @Override
    protected boolean matchesSafely(S valueToAssert) {
        S diff = support.calculate(valueToAssert).minus(value);
        S absoluteDiff = support.absoluteValueOf(diff);
        return support.testIf(absoluteDiff).isLessOrEqualTo(tolerance);
    }

}
