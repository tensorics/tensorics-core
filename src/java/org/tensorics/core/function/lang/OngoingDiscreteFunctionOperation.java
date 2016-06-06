/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.function.lang;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.function.DiscreteFunction;
import org.tensorics.core.function.InterpolatedFunction;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.function.interpolation.InterpolationStrategy;
import org.tensorics.core.math.ExtendedField;

import com.google.common.base.Function;

public class OngoingDiscreteFunctionOperation<X, Y> {

    private final Environment<Y> environment;
    private Function<X, Y> conversion;
    private ExtendedField<Y> field;
    private final DiscreteFunction<X, Y> left;

    public OngoingDiscreteFunctionOperation(Environment<Y> environment, Function<X, Y> conversion,
            DiscreteFunction<X, Y> left) {
        this.environment = environment;
        this.conversion = conversion;
        this.left = left;
        this.field = environment.field();
    }

    public DiscreteFunction<X, Y> plus(DiscreteFunction<X, Y> right) {
        @SuppressWarnings("unchecked")
        InterpolationStrategy<Y> strategy = environment.options().get(InterpolationStrategy.class);
        
  
        InterpolatedFunction<X, Y> rightInterpolated = null; // MathFunctions.interpolated(right, strategy, conversion);
        
        
        MapBackedDiscreteFunction.Builder<X, Y> builder = MapBackedDiscreteFunction.builder();
        
        for (X x : left.definedXValues()) {
         
            
            
            
        }
        
        
        return null;
    }

    public DiscreteFunction<X, Y> minus(DiscreteFunction<X, Y> right) {
        return null;
    }

    public DiscreteFunction<X, Y> times(DiscreteFunction<X, Y> right) {
        return null;
    }

    public DiscreteFunction<X, Y> dividedBy(DiscreteFunction<X, Y> right) {
        return null;
    }

}
