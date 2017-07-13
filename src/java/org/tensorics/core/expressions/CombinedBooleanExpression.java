/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.expressions;

import org.tensorics.core.commons.operations.Conversion;
import org.tensorics.core.tree.domain.Expression;

/**
 * This expression reduces an {@link Expression} of {@link Iterable} of boolean to an {@link Expression} of a
 * {@link Boolean}. The conversion represents the reduction strategy that will be applied on the iterable.
 *
 * @author acalia, caguiler, kfuchsberger
 */
public class CombinedBooleanExpression extends ConversionOperationExpression<Iterable<Boolean>, Boolean> {
    private static final long serialVersionUID = 1L;

    public CombinedBooleanExpression(Conversion<Iterable<Boolean>, Boolean> operation,
            Expression<Iterable<Boolean>> source) {
        super(operation, source);
    }

}
