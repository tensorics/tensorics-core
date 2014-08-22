/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

public enum InstantiatorType {
    CONSTRUCTOR {
        @Override
        public <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass) {
            return new SingleArgumentConstructorInstantiator<>(instanceClass, argumentClass);
        }
    },
    FACTORY_METHOD {
        @Override
        public <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass) {
            return new SingleArgumentFactoryMethodInstantiator<>(instanceClass, argumentClass);
        }
    };

    public abstract <A, R> Instantiator<A, R> createInstantiator(Class<R> instanceClass, Class<A> argumentClass);
}
