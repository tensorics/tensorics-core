/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import com.google.common.reflect.Invokable;

/**
 * This instantiator expects a public constructor with a single argument of a certain type to instantiate a certain
 * class.
 * 
 * @author kfuchsbe
 * @param <A> the type of the argument of the constructor
 * @param <R> the type of the return type (type of the class to instantiate)
 */
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
