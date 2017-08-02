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

package org.tensorics.core.util;

import static java.lang.String.format;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.Beta;

@Beta
public class Reflections {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reflections.class);

    public static final Object invokeUnchecked(Object object, Method getter) {
        try {
            return getter.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException("Invocation of getter '" + getter.getName() + "' resulted in an exception.", e);
        }
    }

    public static List<Method> declaredGetters(Object object) {
        return Arrays.stream(object.getClass().getDeclaredMethods()).filter(m -> (m.getParameterCount() == 0))
                .filter(m -> !m.getReturnType().equals(Void.TYPE)).filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> !Modifier.isStatic(m.getModifiers())).collect(toList());
    }

    public static final void verifyAllDeclaredGettersReturnSomething(Object object) {
        List<Method> getters = declaredGetters(object);

        for (Method getter : getters) {
            Object value = invokeUnchecked(object, getter);
            requireNonNull(value, "value from getter '" + getter.getName() + "' must not be null!");
        }
    }

    public static Object valueOfField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(format("Exception getting the field %s of object %s", field, target), e);
        }
    }

    public static boolean isPublicField(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    public static boolean isFinalField(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    public static boolean isStaticField(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isPublicConstant(Field field) {
        final int modifiers = field.getModifiers();
        return isStatic(modifiers) && isFinal(modifiers) && isPublic(modifiers);
    }

    public static boolean isSerialVersionUid(Field field) {
        return "serialVersionUID".equals(field.getName());
    }

    public static Object valueOfStaticField(Field field) {
        try {
            return field.get(null);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(format("Cannot get the value of the field %s", field), e);
        }
    }

    public static Optional<Object> optionalValueOfStaticField(Field field) {
        try {
            return Optional.ofNullable(field.get(null));
        } catch (Throwable e) {
            LOGGER.warn("Cannot get the value of the field {}. Ignoring it. Throwable message was: {}.", field,
                    e.getMessage());
            return Optional.empty();
        }
    }

    public static Set<Field> constantsFrom(Class<?> constantsContainer) {
        return Stream.of(constantsContainer.getFields()).distinct().filter(Reflections::isPublicConstant)
                .collect(Collectors.toSet());
    }

}
