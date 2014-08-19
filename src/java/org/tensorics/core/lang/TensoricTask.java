package org.tensorics.core.lang;

import java.util.concurrent.Callable;

/**
 * This class is intended to be subclassed in order to have convenient access to the methods in the tensoric field
 * usage. Users of the class have to simply implement the {@link #run()} method, which can use any of the tensoric field
 * usage functionalities and has to return the final value. For convenience, this class already implements the
 * {@link Callable} interface so that it can easily be used in concurrent environments.
 * 
 * @author kaifox
 * @param <E> the type of the elements of the tensors
 * @param <T> the type of the result of the script.
 */
public abstract class TensoricTask<E, T> extends TensoricSupport<E> implements Callable<T> {

    public TensoricTask(EnvironmentImpl<E> environment) {
        super(environment);
    }

    public abstract T run();

    @Override
    public T call() throws Exception {
        return run();
    }

}
