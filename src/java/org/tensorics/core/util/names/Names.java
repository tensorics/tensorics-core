// @formatter:off
/**
*
* This file is part of streaming pool (http://www.streamingpool.org).
*
* Copyright (c) 2017-present, CERN. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
// @formatter:on

package org.tensorics.core.util.names;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

import com.google.common.annotations.Beta;

@Beta
public class Names {

    public static final Function<Object, String> FROM_NAME_METHOD = Names::fromNameMethod;
    public static final Function<Object, String> FROM_GET_NAME_METHOD = Names::fromGetNameMethod;
    public static final Function<Object, String> FROM_SIMPLE_CLASSNAME = Names::fromSimpleClassName;
    public static final Function<Object, String> FROM_OVERRIDDEN_TOSTRING = Names::fromOverriddenToString;

    private static final String GET_NAME_METHOD_NAME = "getName";
    private static final String NAME_METHOD_NAME = "name";
    private static final String TO_STRING_METHOD_NAME = "toString";

    public static final String fromToString(Object object) {
        return Objects.toString(object);
    }

    public static String fromNameMethod(Object o) {
        return nameFromMethodOfName(o, NAME_METHOD_NAME);
    }

    public static String fromGetNameMethod(Object o) {
        return nameFromMethodOfName(o, GET_NAME_METHOD_NAME);
    }

    public static String fromSimpleClassName(Object o) {
        return o.getClass().getSimpleName();
    }

    public static final String fromOverriddenToString(Object object) {
        if (isToStringOverriden(object)) {
            return object.toString();
        }
        return null;
    }

    private static boolean isToStringOverriden(Object object) {
        try {
            return !object.getClass().getMethod(TO_STRING_METHOD_NAME).getDeclaringClass().equals(Object.class);
        } catch (Exception e) {
            /* returning false */
        }
        return false;
    }

    private static String nameFromMethodOfName(Object object, String methodName) {
        Method nameMethod;
        try {
            nameMethod = object.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            /* Null On purpose! */
            return null;
        }
        if (!String.class.isAssignableFrom(nameMethod.getReturnType())) {
            return null;
        }
        try {
            return (String) nameMethod.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return null;
        }
    }

}
