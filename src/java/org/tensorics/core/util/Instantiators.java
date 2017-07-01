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

package org.tensorics.core.util;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;

/**
 * Contains utility methods which help to instantiate classes which follow certain conventions. The most common one is
 * that e.g. a class provides a constructor with one argument of a certain type.
 * 
 * @author kfuchsbe
 */
public final class Instantiators {

    /**
     * Private Constructor to avoid instantiation
     */
    private Instantiators() {
        /* only static methods */
    }

    /**
     * Starting point for fluent clauses to create instantiators for certain type of objects. A typical example for the
     * creation of a certain type of object (Instance) with an argument of a certain type (Argument) could look like
     * this:
     * 
     * <pre>
     * {@code
     *   Instantiator<Argument, Instance> instantiator =
     *      instantiatorFor(Instance.class).withArgumentType(Argument.class);
     * 
     *   Instance instance = instantiator.create(anArgument); // anArgument being of type Argument
     * }
     * </pre>
     * 
     * @param instanceClass the type of the objects to be created by the instantiator
     * @return an objects that provides methods for refinements of the instantiator
     */
    public static <T> OngoingInstantiatorCreation<T> instantiatorFor(Class<T> instanceClass) {
        return new OngoingInstantiatorCreation<>(InstantiatorType.FACTORY_METHOD, instanceClass);
    }

    /**
     * Part of a fluent API to create Instantiators with one arguments.
     * 
     * @author kfuchsbe
     * @param <T> the type of the objects that will be finally created by the instantiators
     */
    public static final class OngoingInstantiatorCreation<T> {
        private final Class<T> instanceClass;
        private final InstantiatorType type;

        /**
         * Constructor, which takes the class of the objects to instantiate, package private because it will be
         * instantiated from within this class only.
         * 
         * @param instanceClass the type of the objects to create
         */
        OngoingInstantiatorCreation(InstantiatorType type, Class<T> instanceClass) {
            this.instanceClass = checkNotNull(instanceClass, "Class to instantiate must not be null!");
            this.type = checkNotNull(type, "Instantiator type must not be null");
        }

        public OngoingInstantiatorCreation<T> ofType(InstantiatorType newType) {
            return new OngoingInstantiatorCreation<>(newType, instanceClass);
        }

        /**
         * Creates the Instantiator for the previously specified object type and the given type of arguments for the
         * object constructor. The returned Instantiator is reusable and thread safe, because it does not contain any
         * state.
         * 
         * @param argumentClass the type of the argument of the constructor for the object
         * @return a new instantiator
         */
        public <A> Instantiator<A, T> withArgumentType(Class<A> argumentClass) {
            Preconditions.checkNotNull(argumentClass, "The type of the constructor argument must not be null!");
            return type.createInstantiator(instanceClass, argumentClass);
        }
    }

}
