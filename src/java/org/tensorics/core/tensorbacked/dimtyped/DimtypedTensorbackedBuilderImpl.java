package org.tensorics.core.tensorbacked.dimtyped;

import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DimtypedTensorbackedBuilderImpl<V, T extends DimtypedTensorbacked<V>, B extends DimtypedTensorbackedBuilder<V, T, B>> implements DimtypedTensorbackedBuilder<V, T, B> {

    private final TensorBuilder<V> delegate;
    private final Class<T> tensorType;
    private final B proxy;

    private DimtypedTensorbackedBuilderImpl(TensorBuilder<V> delegate, Class<B> tensorBuilderType, Class<T> tensorType) {
        this.delegate = requireNonNull(delegate, "delegate must not be null");
        this.proxy = proxy(tensorBuilderType);
        this.tensorType = requireNonNull(tensorType, "tensorType must not be null");
    }

    public static <V, T extends DimtypedTensorbacked<V>, B extends DimtypedTensorbackedBuilder<V, T, B>> B from(TensorBuilder<V> delegate, Class<B> tensorBuilderType, Class<T> tensorType) {
        return new DimtypedTensorbackedBuilderImpl<>(delegate, tensorBuilderType, tensorType).proxy;
    }


    @Override
    public T build() {
        return DimtypedTensorbackeds.create(tensorType, delegate.build());
    }

    @Override
    public B context(Position context) {
        this.delegate.context(context);
        return proxy;
    }

    @Override
    public B context(Object... coordinates) {
        this.delegate.context(coordinates);
        return proxy;
    }


    @Override
    public B putAll(Tensor<V> tensor) {
        delegate.putAll(tensor);
        return proxy;
    }

    @Override
    public B putAll(Position position, Tensor<V> tensor) {
        delegate.putAll(position, tensor);
        return proxy;
    }

    @Override
    public B putAll(Position position, Tensorbacked<V> tensorbacked) {
        delegate.putAll(position, tensorbacked.tensor());
        return proxy;
    }

    @Override
    public B putAll(T tensorBacked) {
        delegate.putAll(tensorBacked.tensor());
        return proxy;
    }

    @Override
    public B put(Position position, V value) {
        delegate.put(position, value);
        return proxy;
    }

    @Override
    public B putAll(Set<Map.Entry<Position, V>> entries) {
        delegate.putAll(entries);
        return proxy;
    }

    @Override
    public B put(Map.Entry<Position, V> entry) {
        delegate.put(entry);
        return proxy;
    }

    @Override
    public B remove(Position position) {
        delegate.remove(position);
        return proxy;
    }

    @Override
    public B putAll(Map<Position, V> newEntries) {
        delegate.putAll(newEntries);
        return proxy;
    }

    @Override
    public B putAll(Position position, Map<Position, V> map) {
        delegate.putAll(position, map);
        return proxy;
    }


    private B proxy(Class<B> tensorBuilderType) {
        return (B) Proxy.newProxyInstance(DimtypedTensorbackedBuilderImpl.class.getClassLoader(), new Class<?>[]{tensorBuilderType}, new DelegatingInvocationHandler<>(tensorBuilderType, this));

    }

    private final static class DelegatingInvocationHandler<V, B extends TensorbackedBuilder<V, ?>> implements InvocationHandler {
        private final B delegate;
        private final Class<?> tensorBuilderType;

        public DelegatingInvocationHandler(Class<?> tensorBuilderType, B delegate) {
            this.tensorBuilderType = requireNonNull(tensorBuilderType, "tensorBuilderType must not be null");
            this.delegate = requireNonNull(delegate, "delegate must not be null");
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

            try {
                return method.invoke(delegate, args);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }
}
