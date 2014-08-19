package org.tensorics.core.tree.domain;

/**
 * An expression is a placeholder for concrete value (called 'resolved' in the following) or a value which has to still
 * be evaluated (deferred).
 * 
 * @author kaifox
 * @param <R> the type of the resulting value
 */
public interface Expression<R> extends Node {

    /**
     * returns {@code true} if the expression contains a concrete value. Thus the method {@link #get()} can be used to
     * retrieve the actual value. If this method returns {@code false}, then the get method will throw an exception.
     * 
     * @return {@code true} if the value can be retrieved, {@code false} otherwise.
     */
    boolean isResolved();

    /**
     * Retrieves the value of the expression. If the expression is deferred, then an expression will be thrown.
     * 
     * @return the value of the expression.
     * @throws ExpressionIsUnresolvedException if the value is not determined.
     */
    R get();

}
