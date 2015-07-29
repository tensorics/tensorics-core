package org.tensorics.core.function;


public interface EnumParametrizedMathFunction<T, E extends Enum<E>> extends MathFunction<T> {

    T get(E parameter);

}
