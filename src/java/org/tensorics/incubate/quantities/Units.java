/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.tensorics.core.tree.domain.Expression;

import com.google.common.reflect.TypeToken;

public final class Units {

    private static final ClassLoader CLASS_LOADER = Units.class.getClassLoader();

    private Units() {
        /* only static methods */
    }

    public static <Q extends Quantity<? super Any>> Q base1(Class<Q> quantityClass, String symbol) {
        return null;
    }

    public static <Q extends Quantity<Any>> Q base(Class<Q> quantityClass, String symbol) {
        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            String methodName = method.getName();
            if ("symbol".equals(methodName)) {
                return symbol;
            }
            throw new IllegalArgumentException("Method '" + methodName + "' is not valid for a base.");
        };

        @SuppressWarnings("unchecked")
        Q unit = (Q) Proxy.newProxyInstance(CLASS_LOADER, new Class<?>[] { quantityClass, Unit.class }, handler);
        return unit;
    }

    public static <Q extends Quantity<Any>> Q derived(Class<? super Q> quantityClass, String symbol,
            Expression<Quantity<Any>> expression) {
        return null;

    }

}
