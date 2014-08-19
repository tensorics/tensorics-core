package org.tensorics.core.expressions;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

/**
 * An expression which contains an operation which will create an object of a certain type just from nothing (with no
 * additional input).
 * 
 * @author kfuchsbe
 * @param <T> the type of the object which will be created by the contained operation.
 */
public class CreationOperationExpression<T> extends AbstractDeferredExpression<T> {

    private final CreationOperation<T> operation;

    public CreationOperationExpression(CreationOperation<T> operation) {
        super();
        this.operation = operation;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    public CreationOperation<T> getOperation() {
        return operation;
    }

}
