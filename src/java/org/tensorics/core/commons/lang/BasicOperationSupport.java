package org.tensorics.core.commons.lang;


/**
 * An interface which simply defines basic operations on a certain type. The main purpose of this interface is mainly to
 * streamline naming and make consistent refactorings easier.
 * 
 * @author kfuchsbe
 * @param <S> the type of the objects on which to operate
 */
public interface BasicOperationSupport<S> {

    OngoingBinaryOperation<S> calculate(S operand);
    
    S negativeOf(S element);

    S inverseOf(S element);

    S squareRootOf(S value);

    S squareOf(S value);
    
    S absoluteValueOf(S value);

}