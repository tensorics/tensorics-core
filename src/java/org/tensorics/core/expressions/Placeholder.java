package org.tensorics.core.expressions;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

/**
 * Represents and unresolved expression which is simply identified by its name.
 *
 * @author kaifox
 * @param <R> the type of the result of the expression
 */
public class Placeholder<R> extends AbstractDeferredExpression<R> {
    private static final long serialVersionUID = 1L;

    private final String name;

    public static <R> Placeholder<R> ofName(String name) {
        return new Placeholder<>(name);
    }
    
    public static <R> Placeholder<R> ofClass(Class<R> clazz) {
        return new Placeholder<>(clazz.getName());
    }

    private Placeholder(String name) {
        this.name = requireNonNull(name, "name must not be null.");
    }

    @Override
    public List<? extends Node> getChildren() {
        return Collections.emptyList();
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Placeholder [name=" + name + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Placeholder<?> other = (Placeholder<?>) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
