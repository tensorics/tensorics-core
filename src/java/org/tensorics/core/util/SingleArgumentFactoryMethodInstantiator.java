/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.common.reflect.Invokable;

public class SingleArgumentFactoryMethodInstantiator<A, R> extends SingleArgumentInvokableInstantiator<A, R> {

    public SingleArgumentFactoryMethodInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
        super(instanceClass, constructorArgumentClass);
    }

    @Override
    protected Invokable<R, R> invokableFor(Class<A> argumentClass) {
        for (Method method : instanceClass.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getReturnType().isAssignableFrom(instanceClass)
                    && method.getParameterTypes().length == 1
                    && method.getParameterTypes()[0].isAssignableFrom(argumentClass)) {
                @SuppressWarnings("unchecked")
                Invokable<R, R> invokable = (Invokable<R, R>) Invokable.from(method);
                return invokable;
            }
        }
        throw new IllegalArgumentException("No static factory method found in class '" + instanceClass
                + "' for argument type '" + argumentClass + "'");
    }

}
