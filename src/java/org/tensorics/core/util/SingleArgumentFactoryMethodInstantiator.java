/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.google.common.reflect.Invokable;

/**
 * This instantiator expects a factory method with one single argument a class. The decision, which factory method to
 * use is based on the following search process: For all the declared methods in the given class check, if the following
 * criteria are fulfilled:
 * <ol>
 * <li>the method is public
 * <li>the method is static
 * <li>the return type is the desired class to instantiate
 * <li>the method takes exactly one parameter
 * <li>the one parameter type matches the desired parameter type
 * </ol>
 * 
 * @author kfuchsbe
 * @param <A> the type of the argument
 * @param <R> the type of the return type (the class to instantiate)
 */
public class SingleArgumentFactoryMethodInstantiator<A, R> extends SingleArgumentInvokableInstantiator<A, R> {

    public SingleArgumentFactoryMethodInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
        super(instanceClass, constructorArgumentClass);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Invokable<R, R> invokableFor(Class<A> argumentClass) {
        for (Method method : instanceClass.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())
                    && method.getReturnType().isAssignableFrom(instanceClass) && method.getParameterTypes().length == 1
                    && method.getParameterTypes()[0].isAssignableFrom(argumentClass)) {
                return (Invokable<R, R>) Invokable.from(method);
            }
        }
        throw new IllegalArgumentException("No static factory method found in class '" + instanceClass
                + "' for argument type '" + argumentClass + "'");
    }

}
