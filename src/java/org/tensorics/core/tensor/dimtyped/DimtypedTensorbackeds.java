package org.tensorics.core.tensor.dimtyped;

import org.tensorics.core.tensor.Tensor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static java.util.Objects.requireNonNull;

public class DimtypedTensorbackeds {

    public static final <V, T extends DimtypedTensorbacked<V>> T create(Class<T> tensorType, Tensor<V> delegate) {
        if (DimtypedTensorbacked.class.equals(tensorType)) {
            throw new IllegalArgumentException("The class of the tensor must be a subtype of '" + DimtypedTensorbacked.class + "', but not the class itself.");
        }
        return (T) Proxy.newProxyInstance(DimtypedTensorbackeds.class.getClassLoader(), new Class<?>[]{tensorType}, new DelegatingInvocationHandler<>(tensorType, delegate));
    }

    private final static class DelegatingInvocationHandler<V> implements InvocationHandler {
        private final Tensor<V> delegate;
        private final Class<?> tensorType;

        public DelegatingInvocationHandler(Class<?> tensorType, Tensor<V> delegate) {
            this.tensorType = requireNonNull(tensorType, "tensorType must not be null");
            this.delegate = requireNonNull(delegate, "delegate must not be null");
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (tensorType.equals(method.getDeclaringClass())) {
                if ("get".equals(method.getName())) {
                    return delegate.get(args);
                } else {
                    throw new UnsupportedOperationException("The method " + method + " is not supported by the proxy!");
                }
            }
            try {
                return method.invoke(delegate, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }
}
