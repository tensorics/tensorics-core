package org.tensorics.core.tree.domain;

/**
 * Exception which is thrown, if it is tried to get the value of an expression which is deferred.
 * 
 * @author kaifox
 */
public class ExpressionIsUnresolvedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExpressionIsUnresolvedException() {
        super();
    }

    public ExpressionIsUnresolvedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionIsUnresolvedException(String message) {
        super(message);
    }

    public ExpressionIsUnresolvedException(Throwable cause) {
        super(cause);
    }

}
