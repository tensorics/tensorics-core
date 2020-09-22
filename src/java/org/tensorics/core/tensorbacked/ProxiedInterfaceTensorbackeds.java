package org.tensorics.core.tensorbacked;

import static java.util.Objects.requireNonNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.util.JavaVersions;

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
                MethodCallHandler callHandler = callHandlerFor(intfc, method);
                return callHandler.invoke(proxy, args);
            }

            if (Tensorbacked.class.equals(method.getDeclaringClass()) && "tensor".equals(method.getName())
                    && (method.getParameterCount() == 0) && method.getReturnType().isAssignableFrom(Tensor.class)) {
                return delegate;
            }

            if ("toString".equals(method.getName()) && (args == null)) {
                return toString();
            }
            if ("hashCode".equals(method.getName()) && (args == null)) {
                return hashCode();
            }
            if ("equals".equals(method.getName()) && (args.length == 1)) {
                return equals(args[0]);
            }

            throw new UnsupportedOperationException("The method " + method + " is not supported by the proxy!");
        }

        @Override
        public int hashCode() {
            return Objects.hash(delegate, intfc);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DelegatingInvocationHandler other = (DelegatingInvocationHandler) obj;
            return Objects.equals(delegate, other.delegate) && Objects.equals(intfc, other.intfc);
        }

        @Override
        public String toString() {
            return "Tensorbacked [intfc=" + intfc + ", delegate=" + delegate + "]";
        }
    }

    @FunctionalInterface
    private interface MethodCallHandler {
        Object invoke(Object proxy, Object[] args) throws Throwable;
    }

    private static MethodCallHandler callHandlerFor(Class<?> intfc, Method method) {
        MethodHandle handle = getMethodHandle(intfc, method);
        return (proxy, args) -> handle.bindTo(proxy).invokeWithArguments(args);
    }

    private static MethodHandle getMethodHandle(Class<?> intfc, Method method) {
        Class<?> declaringClass = method.getDeclaringClass();

        /*
         * XXX: This is not too nice: For some reason, the original code (java) 8 did not run in java 11 anymore.
         * And vice versa ... what the exact border is where it stopped working was not full checked.
         * So potentially more research would be needed here.
         */
        if (JavaVersions.isAtLeastJava11()) {
            try {
                return MethodHandles.lookup().findSpecial(intfc, method.getName(),
                        MethodType.methodType(method.getReturnType(), method.getParameterTypes()), intfc);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
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
