/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Can instantiate a certain type of classes, which expect exactly one argument of a certain type in the constructor.
 * <p>
 * This class is immutable and thus reusable and thread safe.
 * 
 * @author kfuchsbe
 * @param <A> the type of the constructor argument
 * @param <R> the type of the resulting instance
 */
public final class SingleArgumentConstructorInstantiator<A, R> implements Instantiator<A, R> {

    private final Class<R> instanceClass;
    private final Constructor<R> constructor;

    public SingleArgumentConstructorInstantiator(Class<R> instanceClass, Class<A> constructorArgumentClass) {
        this.instanceClass = instanceClass;
        this.constructor = constructorFor(constructorArgumentClass);
    }

    @Override
    public <A1 extends A> R create(A1 constructorArgument) {
        checkNotNull(constructorArgument, "The constructor argument must not be null!");
        try {
            return constructor.newInstance(constructorArgument);
        } catch (InstantiationException | IllegalAccessException | //
                IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException("Constructor of class '" + instanceClass
                    + "' could not be invoked with argument '" + constructorArgument + "'", e);
        }
    }

    private Constructor<R> constructorFor(Class<A> constructorArgumentClass) {
        try {
            return instanceClass.getConstructor(constructorArgumentClass);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalArgumentException("The class '" + instanceClass
                    + "' does not have a public constructor requiring exactly one argument of type '"
                    + constructorArgumentClass + "'.", e);
        }
    }

}