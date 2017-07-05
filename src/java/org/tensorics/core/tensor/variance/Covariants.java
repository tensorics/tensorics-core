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
