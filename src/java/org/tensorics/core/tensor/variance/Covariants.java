/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensor.variance;

import java.lang.reflect.ParameterizedType;

import org.tensorics.core.util.Classes;
import org.tensorics.core.util.Instantiator;
import org.tensorics.core.util.Instantiators;

/**
 * Utility methods used by classes related to the covariance mechanism
 * 
 * @author kfuchsbe
 */
public final class Covariants {

    private Covariants() {
        /* only static methods */
    }

    /**
     * Retrieves an instantiator, which can create instances of the given covariant class from the bare coordinate
     * instances. The type of the coordinate class (constructor argument for the covariant class) is taken from the
     * generic argument of the passed in class. The returned instantiator is re usable and thread safe.
     * 
     * @param covariantClass the type of objects that shall be instantated
     * @return an instantiator to create instances of the given covariant class
     */
    public static <C, CC extends Covariant<C>> Instantiator<C, CC> instantiatorFor(Class<CC> covariantClass) {
        return Instantiators.instantiatorFor(covariantClass).withArgumentType(coordinateClassFor(covariantClass));
    }

    @SuppressWarnings("unchecked")
    public static <C> Class<C> coordinateClassFor(Class<? extends Covariant<C>> covariantClass) {
        ParameterizedType genericSuperclass = Classes.findGenericSuperclassOfType(covariantClass, Covariant.class);
        return (Class<C>) genericSuperclass.getActualTypeArguments()[0];
    }

    public static boolean isCovariant(Class<?> dimension) {
        return Covariant.class.isAssignableFrom(dimension);
    }

    public static boolean isContravariant(Class<?> dimension) {
        return !isCovariant(dimension);
    }

    public static Class<?> contravariantOf(Class<?> dimension) {
        if (isCovariant(dimension)) {
            return coordinateClassFor(Covariants.covariantClassOf(dimension));
        } else {
            return dimension;
        }
    }

    @SuppressWarnings("unchecked")
    private static <C> Class<? extends Covariant<C>> covariantClassOf(Class<?> dimension) {
        return (Class<? extends Covariant<C>>) dimension;
    }

    public static boolean isCoContraPair(Class<?> leftDimension, Class<?> rightDimension) {
        return (isCovariant(leftDimension) && contravariantOf(leftDimension).equals(rightDimension))
                || (isCovariant(rightDimension) && contravariantOf(rightDimension).equals(leftDimension));
    }

}
