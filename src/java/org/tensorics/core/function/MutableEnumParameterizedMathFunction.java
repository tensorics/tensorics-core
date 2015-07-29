package org.tensorics.core.function;

public interface MutableEnumParameterizedMathFunction<T, E extends Enum<E>> extends EnumParametrizedMathFunction<T, E> {

    void set(E param, T value);
}
