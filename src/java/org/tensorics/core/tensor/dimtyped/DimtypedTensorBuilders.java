package org.tensorics.core.tensor.dimtyped;

import com.sun.jdi.InvocationException;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.TensorBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class DimtypedTensorBuilders {

    public static final <V, T extends DimtypedTensor<V>, B extends DimtypedTensorBuilder<V, T, B>> B create(Class<B> tensorBuilderType, TensorBuilder<V> delegate, Class<T> tensorType) {
        Types.requireTrueSubSuper(tensorBuilderType, DimtypedTensorBuilder.class);
        Types.requireTrueSubSuper(tensorType, DimtypedTensor.class);
        return (B) Proxy.newProxyInstance(DimtypedTensorBuilders.class.getClassLoader(), new Class<?>[]{tensorBuilderType}, new DelegatingInvocationHandler<>(tensorBuilderType, delegate, tensorType));
    }

    private final static class DelegatingInvocationHandler<V> implements InvocationHandler {
        private final TensorBuilder<V> delegate;
        private final Class<?> tensorBuilderType;
        private final Class<? extends DimtypedTensor<V>> tensorType;

        public DelegatingInvocationHandler(Class<?> tensorBuilderType, TensorBuilder<V> delegate, Class<? extends DimtypedTensor<V>> tensorType) {
            this.tensorBuilderType = requireNonNull(tensorBuilderType, "tensorBuilderType must not be null");
            this.delegate = requireNonNull(delegate, "delegate must not be null");
            this.tensorType = requireNonNull(tensorType, "tensorType must not be null");
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (tensorBuilderType.equals(method.getDeclaringClass())) {
                if ("put".equals(method.getName())) {
                    Object[] coordinates = Arrays.copyOf(args, args.length - 1);
                    Object value = args[args.length - 1];
                    delegate.put(Position.of(coordinates), (V) value);
                    return proxy;
                } else {
                    throw new UnsupportedOperationException("The method " + method + " is not supported by the proxy!");
                }
            }
            if ("build".equals(method.getName()) && method.getParameterCount() == 0) {
                return DimtypedTensors.create(tensorType, delegate.build());
            }

            try {
                return method.invoke(delegate, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }
}
