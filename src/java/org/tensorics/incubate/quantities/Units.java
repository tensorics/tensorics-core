/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Units {

    private Units() {
        /* only static methods */
    }

    public static <Q extends Quantity<Any>> Q unit(Class<? super Q> quantityClass, String symbol) {
        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            String methodName = method.getName();
            if ("symbol".equals(methodName)) {
                return symbol;
            }
            throw new IllegalArgumentException("Method '" + methodName + "' is not valid for a quantity.");
        };

        @SuppressWarnings("unchecked")
        Q unit = (Q) Proxy.newProxyInstance(Units.class.getClassLoader(), new Class<?>[] { quantityClass, Unit.class },
                handler);
        return unit;
    }

}
