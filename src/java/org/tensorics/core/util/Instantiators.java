/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

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
     * <code>
     *   Instantiator<Argument, Instance> instantiator = 
     *      instantiatorFor(Instance.class).withArgumentType(Argument.class);
     * 
     *   Instance instance = instantiator.create(anArgument); // anArgument being of type Argument
     * </code>
     * </pre>
     * 
     * @param instanceClass the type of the objects to be created by the instantiator
     * @return an objects that provides methods for refinements of the instantiator
     */
    public static <T> OngoingInstantiatorCreation<T> instantiatorFor(Class<T> instanceClass) {
        return new OngoingInstantiatorCreation<>(instanceClass);
    }

    /**
     * Part of a fluent API to create Instantiators with one arguments.
     * 
     * @author kfuchsbe
     * @param <T> the type of the objects that will be finally created by the instantiators
     */
    public static final class OngoingInstantiatorCreation<T> {
        private final Class<T> instanceClass;

        /**
         * Constructor, which takes the class of the objects to instantiate, package private because it will be
         * instantiated from within this class only.
         * 
         * @param instanceClass the type of the objects to create
         */
        OngoingInstantiatorCreation(Class<T> instanceClass) {
            this.instanceClass = Preconditions.checkNotNull(instanceClass, "Class to instantiate must not be null!");
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
            return new SingleArgumentInvokableInstantiator<>(instanceClass, argumentClass);
        }
    }

}
