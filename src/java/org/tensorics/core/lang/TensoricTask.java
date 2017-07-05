// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on
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
