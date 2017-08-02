/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static com.google.common.collect.ImmutableSortedMap.toImmutableSortedMap;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;
import com.google.common.reflect.Invokable;

public final class CaseMatchings {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaseMatchings.class);

    private static final Comparator<Class<?>> SUBTYPE_BEFORE_SUPERTYPE = (Class<?> o1, Class<?> o2) -> {
        if (o1.equals(o2)) {
            return 0;
        }
        if (o2.isAssignableFrom(o1) && !o1.isAssignableFrom(o2)) {
            return -1;
        }
        if (o1.isAssignableFrom(o2) && !o2.isAssignableFrom(o1)) {
            return 1;
        }
        return o1.getName().compareTo(o2.getName());
    };

    private static final String REPR_METHOD_NAME = "repr";

    private CaseMatchings() {
        /* Only static methods */
    }

    public static final <T, R, CT> ClassDispatcher<T, R, CT> from(Class<R> returnType, Class<CT> caseBranchClass) {
        return from(returnType, caseBranchClass, new ClassInstantiator<>(caseBranchClass));
    }

    public static final <T, R, CT> ClassDispatcher<T, R, CT> from(Class<R> returnType, Class<CT> caseBranchClass,
            Supplier<CT> instanceSupplier) {
        Set<Invokable<CT, ?>> dispatchingMethods = methods(caseBranchClass);
        assertValidReprMethods(dispatchingMethods, returnType);
        return new ClassDispatcher<>(caseBranchClass, instanceSupplier, sortByHierarchy(dispatchingMethods));
    }

    private static <CT, R> Class<?> typeOfOnlyParameter(Invokable<CT, R> invokable) {
        return Iterables.getOnlyElement(invokable.getParameters()).getType().getRawType();
    }

    @SuppressWarnings("unchecked")
    private static <T> Set<Invokable<T, ?>> methods(Class<T> caseBranchClass) {
        // @formatter:off
        return Arrays.stream(caseBranchClass.getDeclaredMethods())
                .map(m -> (Invokable<T, ?>) Invokable.from(m))
                /* Somehow in the following code a method reverence (Invokable::isPublic) 
                 * does not work. Not understood.*/
                .filter(i -> i.isPublic())
                /* static methods are not excluded. They work.*/
                .collect(Collectors.toSet());
        // @formatter:on
    }

    @SuppressWarnings("unchecked")
    private static <T, R> SortedMap<Class<?>, Invokable<T, R>> sortByHierarchy(Set<Invokable<T, ?>> methods) {
        return methods.stream().map(i -> (Invokable<T, R>) i).collect(
                toImmutableSortedMap(SUBTYPE_BEFORE_SUPERTYPE, CaseMatchings::typeOfOnlyParameter, identity()));
    }

    private static <T, R> void assertValidReprMethods(Set<Invokable<T, ?>> methods, Class<R> returnType) {
        Set<Invokable<T, ?>> invalidMethods = new HashSet<>();
        for (Invokable<T, ?> method : methods) {
            if ((!method.getReturnType().isSubtypeOf(returnType)) //
                    || (method.getParameters().size() != 1) //
                    || !REPR_METHOD_NAME.equals(method.getName())) {
                invalidMethods.add(method);
            }
        }
        if (!invalidMethods.isEmpty()) {
            throw new IllegalArgumentException(
                    "All public methods in a representer class " + "must fulfill the following contract:\n" //
                            + "*) name=='" + REPR_METHOD_NAME + "'\n" //
                            + "*) one single parameter\n" //
                            + "*) All the same return type (" + returnType + " in this case)\n" //
                            + "The following methods do not fulfill this contract: " + invalidMethods);
        }
    }

    public static final class ClassDispatcher<T, R, CT> implements BiFunction<T, Function<Object, R>, R> {

        private final Supplier<CT> instanceSupplier;
        private final Class<CT> representerClass;
        private final SortedMap<Class<?>, Invokable<CT, R>> methods;

        public ClassDispatcher(Class<CT> representerClass, Supplier<CT> instanceSupplier,
                SortedMap<Class<?>, Invokable<CT, R>> methods) {
            this.instanceSupplier = requireNonNull(instanceSupplier, "instanceSupplier must not be null");
            this.representerClass = requireNonNull(representerClass, "representerClass must not be null");
            this.methods = requireNonNull(methods, "dispatchingMethods must not be null");
            assertSupplierCreatesInstancesWhenRecursive();
        }

        private void assertSupplierCreatesInstancesWhenRecursive() {
            CT firstInstance = instanceSupplier.get();
            if (firstInstance instanceof AbstractRecursiveRepresenter) {
                CT secondInstance = instanceSupplier.get();
                if (firstInstance == secondInstance) {
                    throw new IllegalStateException("The provided supplier '" + instanceSupplier + "' for class '"
                            + representerClass.getCanonicalName() + "' seems not creat new instances on each call. "
                            + "This is forbidden if the instances inherit from '"
                            + AbstractRecursiveRepresenter.class.getSimpleName()
                            + "' (which is the case), because each instance gets a recursion callback injected, "
                            + "which would result in a non-threadsafe situation if instances would be reused.");
                }
            }
        }

        @Override
        public R apply(T t, Function<Object, R> callback) {
            Objects.requireNonNull(callback, "recursionCallback must not be null.");
            CT instanceToDispatchTo = newInstance(callback);

            for (Entry<Class<?>, Invokable<CT, R>> entry : this.methods.entrySet()) {
                Class<?> parameterType = entry.getKey();
                if (parameterType.isInstance(t)) {
                    Invokable<CT, R> invokable = entry.getValue();
                    try {
                        R result = invokable.invoke(instanceToDispatchTo, t);
                        if (result != null) {
                            return result;
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Could not invoke {}.", invokable, e);
                    }
                }
            }

            return null;
        }

        private CT newInstance(Function<Object, R> callback) {
            CT instance = instanceSupplier.get();
            if (instance instanceof AbstractRecursiveRepresenter) {
                /*
                 * NOTE: indeed, the following cast is not *fully* safe: the type parameter in the abstract representer
                 * does not enforce any type of the return type of any of the repr methods. However, it would make no
                 * sense for the implementer, because he could not successfully use the recursion ... So not safe, but
                 * left here until we have a better idea ;-)
                 */
                @SuppressWarnings("unchecked")
                AbstractRecursiveRepresenter<R> castedMatching = (AbstractRecursiveRepresenter<R>) instance;
                if (castedMatching.recursion != null) {
                    throw new IllegalArgumentException("The instance provided by supplier '" + instanceSupplier
                            + "' provided an instance which is a child of '"
                            + AbstractRecursiveRepresenter.class.getSimpleName()
                            + "', has already a non-null value for the recursion field. "
                            + "This might be a hint that the supplier reuses instances. "
                            + "However, the supplier MUST provide a new instance at each call.");
                }
                castedMatching.recursion = callback;
            }
            return instance;
        }

    }

    private static class ClassInstantiator<CT> implements Supplier<CT> {

        private final Class<CT> classToInstantiate;

        public ClassInstantiator(Class<CT> classToInstantiate) {
            this.classToInstantiate = requireNonNull(classToInstantiate, "classToInstantiate must not be null");
        }

        @Override
        public CT get() {
            try {
                return classToInstantiate.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException("Class '" + classToInstantiate + "' could not be instantiated.", e);
            }
        }

    }

}
