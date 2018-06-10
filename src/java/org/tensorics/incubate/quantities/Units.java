/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;

import org.tensorics.core.tree.domain.Expression;

import com.google.common.collect.ImmutableMap;

public final class Units {

    private static final ClassLoader CLASS_LOADER = Units.class.getClassLoader();

    private Units() {
        /* only static methods */
    }

    public static <Q extends Quantity<Any>> Q base(Class<Q> quantityClass, String symbol) {
        Map<String, Object> returnValues = ImmutableMap.of("symbol", symbol, "toString", symbol, "hashCode",
                Objects.hash(quantityClass, symbol));
        return proxyFor(returnValues, new Class<?>[] { quantityClass, Unit.class });
    }

    public static <Q extends Quantity<Any>> Q derived(Class<? super Q> quantityClass, String symbol,
            Expression<Quantity<Any>> expression) {
        Map<String, Object> returnValues = ImmutableMap.of("symbol", symbol, "toString", symbol, "expression",
                expression, "hashCode", Objects.hash(quantityClass, symbol, expression));
        return proxyFor(returnValues, new Class<?>[] { quantityClass, Unit.class, DerivedQuantity.class });
    }

    private static <Q extends Quantity<Any>> Q proxyFor(Map<String, Object> returnValues, Class<?>[] interfaces) {
        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {

            String methodName = method.getName();
            Object returnVal = returnValues.get(methodName);
            if (returnVal != null) {
                return returnVal;
            }
            throw new IllegalArgumentException("Method '" + methodName + "' is not valid for a Unit.");
        };

        @SuppressWarnings("unchecked")
        Q unit = (Q) Proxy.newProxyInstance(CLASS_LOADER, interfaces, handler);
        return unit;
    }

}
