/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.tree.domain.Expression;

import com.google.common.base.Function;

public class FunctionExpressionSupportWithConversion<X extends Comparable<? super X>, Y> {
    
    private Environment<Y> environment;
    private Function<X, Y> conversion;

    public FunctionExpressionSupportWithConversion(Environment<Y> environment, Function<X, Y> conversion) {
        this.environment = environment;
        this.conversion = conversion;
    }
   
    public Expression<Y> averageOf(Expression<DiscreteFunction<X, Y>> function) {
        //new DiscreteFunctionExpression(repository.avg(), function);
        return null;
    }

}
