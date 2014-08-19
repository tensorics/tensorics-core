/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is intended to be used on classes implementing the Tensorbacked interface to define the desired dimensions on
 * them. It will be used by the framework, to decide e.g. what dimensions to reduce and to perform precondition-checks
 * before creating the corresponding instances. It takes an array of classes as argument, to describe which dimensions
 * the tensor backed instance expects.
 * 
 * @author kfuchsbe
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dimensions {
    Class<?>[] value() default {}; // NOSONAR (no whitespace before }
}
