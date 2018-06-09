/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tensorics.incubate.quantities.Quantity;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(Factors.class)
public @interface Factor {

    @SuppressWarnings("rawtypes")
    Class<? extends Quantity> value();

    String exponent() default "1";

}
