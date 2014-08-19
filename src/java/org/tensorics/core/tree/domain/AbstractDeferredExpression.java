package org.tensorics.core.tree.domain;

/**
 * A base class for all kind of deferred (unresolved) expressions. All the methods of the expression interface are
 * implemented here and cannot be overridden. However, the subclasses can / have to provide the necessary informations
 * for the corresponding resolvers.
 * 
 * @author kfuchsbe
 * @param <R> the type of the resolved value of the expression.
 */
public abstract class AbstractDeferredExpression<R> implements Expression<R> {

    private static final boolean RESOLVED = false;

    @Override
    public final boolean isResolved() {
        return RESOLVED;
    }

    @Override
    public final R get() {
        throw new ExpressionIsUnresolvedException("It is not possible to get the value of a deferred expression ('"
                + this.toString() + "')");
    }

}