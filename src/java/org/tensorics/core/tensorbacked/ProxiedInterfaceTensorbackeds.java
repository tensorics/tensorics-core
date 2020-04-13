package org.tensorics.core.tensorbacked;

import org.tensorics.core.tensor.Tensor;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.Objects.requireNonNull;

public final class ProxiedInterfaceTensorbackeds {

    private ProxiedInterfaceTensorbackeds() {
        /* only static methods */
    }

    public static <V, T extends Tensorbacked<V>> T create(Class<T> tensorbackedType, Tensor<V> tensor) {
        if (!tensorbackedType.isInterface()) {
            throw new IllegalArgumentException("The given tensorbacked type '" + tensorbackedType
                    + "' is not an interface! Therefore, it cannot be instantiated by proxying!");
        }
        return (T) Proxy.newProxyInstance(Tensorbacked.class.getClassLoader(), new Class<?>[] { tensorbackedType },
                new DelegatingInvocationHandler<>(tensor, tensorbackedType));
    }

    private final static class DelegatingInvocationHandler<V, T extends Tensorbacked<V>> implements InvocationHandler {
        private final Tensor<V> delegate;
        private final Class<T> intfc;

        public DelegatingInvocationHandler(Tensor<V> delegate, Class<T> intfc) {
            this.delegate = requireNonNull(delegate, "delegate must not be null");
            this.intfc = requireNonNull(intfc, "interface must not be null");
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isDefault()) {
                return MethodHandles.lookup()
                        .findSpecial(intfc, method.getName(),
                                MethodType.methodType(method.getReturnType(), method.getParameterTypes()), intfc)
                        .bindTo(proxy).invokeWithArguments(args);

            }

            if (Tensorbacked.class.equals(method.getDeclaringClass()) && "tensor".equals(method.getName())
                    && (method.getParameterCount() == 0) && method.getReturnType().isAssignableFrom(Tensor.class)) {
                return delegate;
            }

            throw new UnsupportedOperationException("The method " + method + " is not supported by the proxy!");
        }
    }

    @FunctionalInterface
    private interface MethodCallHandler {
        Object invoke(Object proxy, Object[] args) throws Throwable;
    }

    @FunctionalInterface
    private interface MethodInterpreter extends InvocationHandler {

        @Override
        default Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodCallHandler handler = interpret(method);
            return handler.invoke(proxy, args);
        }

        MethodCallHandler interpret(Method method);
    }

    private static class DefaultMethodCallHandler {

        private DefaultMethodCallHandler() {
        }

        private static final ConcurrentMap<Method, MethodCallHandler> cache = new ConcurrentHashMap<>();

        private static MethodCallHandler forMethod(Method method) {
            return cache.computeIfAbsent(method, m -> {
                MethodHandle handle = getMethodHandle(m);
                return (proxy, args) -> handle.bindTo(proxy).invokeWithArguments(args);
            });
        }

        private static MethodHandle getMethodHandle(Method method) {
            Class<?> declaringClass = method.getDeclaringClass();

            try {
                Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                        .getDeclaredConstructor(Class.class, int.class);
                constructor.setAccessible(true);

                return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE).unreflectSpecial(method,
                        declaringClass);
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
