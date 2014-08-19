/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.iterable.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.expressions.ConversionOperationExpression;
import org.tensorics.core.tree.domain.Expression;

/**
 * An unresolved expression which uses an operation on iterables to produce one value of the same type. An instance will
 * contain the operation to act on the iterable as well as an expression for the iterable on which the operation will
 * have to be performed.
 * 
 * @author kfuchsbe
 * @param <T> the type of the elements of the iterable and the result of this expression.
 */
public class IterableOperationExpression<T> extends ConversionOperationExpression<Iterable<T>, T> {

    public IterableOperationExpression(Conversion<Iterable<T>, T> operation, Expression<Iterable<T>> iterable) {
        super(operation, iterable);
    }

}
