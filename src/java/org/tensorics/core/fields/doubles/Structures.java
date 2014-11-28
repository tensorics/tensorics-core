/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.fields.doubles;

import org.tensorics.core.math.Cheating;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.math.Math;
import org.tensorics.core.math.impl.ExtendedFieldImpl;
import org.tensorics.core.math.structures.ringlike.Field;
import org.tensorics.core.math.structures.ringlike.OrderedField;

/**
 * Utility class that provides methods to access mathematical structures which are available in the tensorics core
 * package.
 * 
 * @author kfuchsbe
 */
public final class Structures {

    private static final DoubleField DOUBLE_FIELD = new DoubleField();
    private static final DoubleMath DOUBLE_MATH = new DoubleMath();
    private static final DoubleCheating DOUBLE_CHEATING = new DoubleCheating();

    /**
     * private constructor to avoid instantiation
     */
    private Structures() {
        /* Only static methods */
    }

    public static ExtendedField<Double> doubles() {
        return extended(DOUBLE_FIELD, DOUBLE_MATH, DOUBLE_CHEATING);
    }

    /**
     * Creates a more explicit view of the given field together with the given implementation of mathematical functions.
     * This way more efficient implementations can be used.
     * 
     * @param field the field for which to create the explicit view.
     * @param math the class providing mathematical functions.
     * @param cheating the class that provides some methods for cheating the field-framework.
     * @return a view on the field, which provides more convenience methods.
     */
    public static <T> ExtendedField<T> extended(OrderedField<T> field, Math<T> math, Cheating<T> cheating) {
        return new ExtendedFieldImpl<>(field, math, cheating);
    }

}
