/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Provides utility methods for reflection based tasks, for example to detect types of generic parameters.
 * 
 * @author kfuchsbe
 */
public final class Classes {

    /**
     * Private constructor to avoid instantiation
     */
    private Classes() {
        /* only static methods */
    }

    public static ParameterizedType findGenericSuperclassOfType(Class<?> clazz, Class<?> classToSearch) {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) {
            throw new IllegalArgumentException("The class '" + classToSearch
                    + "' is not a super class of the given class.");
        }

        Type type = clazz.getGenericSuperclass();
        if ((type instanceof ParameterizedType)) {
            if (!superClass.equals(classToSearch)) {
                throw new IllegalArgumentException("The covariant coordinate class does not specify directly the "
                        + "type of coordinate, but seems to use another generic class in the inheritance hierarchy. "
                        + "This is currently not supported.");
            }
            return (ParameterizedType) type;
        }
        return findGenericSuperclassOfType(superClass, classToSearch);
    }

}
