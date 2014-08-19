package org.tensorics.core.commons.lang;

/**
 * An interface for parts of the tensorics fluent API to the describe the right hand clause of a binary operation. The
 * main purpose of this interfacce is to streamline the naming for implementations on different types.
 * 
 * @author agorzaws
 * @param <T> the type of the scalar values on which the operations take place
 */
public interface OngoingBinaryOperation<T> {

    T plus(T rightOperand);

    T minus(T rightOperand);

    T times(T rightOperand);

    T dividedBy(T rightOperand);

    T toThePowerOf(T power);

    T root(T element);

}