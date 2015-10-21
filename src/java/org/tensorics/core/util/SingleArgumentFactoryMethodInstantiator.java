// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
 ******************************************************************************/
// @formatter:on

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
