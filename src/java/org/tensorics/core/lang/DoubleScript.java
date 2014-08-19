package org.tensorics.core.lang;

import org.tensorics.core.fields.doubles.Structures;

/**
 * A convenience class designed for inheritance, already providing the doubles field in the constructor.
 * 
 * @author kaifox
 * @param <R> the type of the return value of the script
 */
public abstract class DoubleScript<R> extends TensoricScript<Double, R> {

    public DoubleScript() {
        super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
    }
}
