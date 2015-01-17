package org.tensorics.core.tensor.specific;

/**
 * This exception will be thrown if an array of a too large size would be required to store all the tensor values.
 * 
 * @author kfuchsbe
 */
public class TooLargeForArrayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TooLargeForArrayException(String message) {
        super(message);
    }

}
