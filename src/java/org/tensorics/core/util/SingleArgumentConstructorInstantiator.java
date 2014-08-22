/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import com.google.common.reflect.Invokable;

public class SingleArgumentConstructorInstantiator<A, R> extends SingleArgumentInvokableInstantiator<A, R> {

    public SingleArgumentConstructorInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
        super(instanceClass, constructorArgumentClass);
    }

    @Override
    protected Invokable<R, R> invokableFor(Class<A> constructorArgumentClass) {
        try {
            return Invokable.from(instanceClass.getConstructor(constructorArgumentClass));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("The class '" + instanceClass
                    + "' does not have a public constructor requiring exactly one argument of type '"
                    + constructorArgumentClass + "'.", e);
        }
    }

}
